class BigInt {
    constructor(s) {
        s = s.replace(/^(0+)/gi, "");
        if (s.length === 0) {
            s = "0";
        }
        // u为false代表负数
        this.u = s.charAt(0) !== "-";
        let startI = this.u ? 0 : 1;
        // 整数字符串倒序
        this.s = [];
        for (let i = s.length - 1; i >= startI; i--) {
            this.s.push(s.charAt(i));
        }
        // 乘10的c次方
        this.c = 0;
    }

    /**
     * 是否比另一个数小
     * @param other
     */
    lt(other) {
        if (other) {
            let t1 = this.toZeroC().toStringValue();
            let t2 = other.toZeroC().toStringValue();
            let abs = Math.abs(t1.length - t2.length);
            for (let i = 0; i < abs; i++) {
                if (t1.length < t2.length) {
                    t1 = "0" + t1;
                }
                if (t2.length < t1.length) {
                    t2 = "0" + t2;
                }
            }
            return t1 < t2;
        }
    }

    /**
     * 得到字符串的数值
     * @returns {string}
     */
    toStringValue() {
        let s = "";
        for (let i = this.s.length - 1; i >= 0; i--) {
            s += this.s[i];
        }
        return s;
    }

    /**
     * 将c次方转为0次方,不改变当前对象，返回转换结果
     */
    toZeroC() {
        let result = new BigInt(this.toStringValue());
        for (let i = 0; i < this.c; i++) {
            result.s.unshift("0");
        }
        return result;
    }

    setC(c) {
        this.c = c;
    }

    /**
     * 拆成两数之和的形式
     * @param n c
     */
    toSplit(n) {
        let result = [];
        let num1S = "";
        let num2S = "";
        for (let i = this.s.length - 1; i >= 0; i--) {
            if (num1S.length < this.s.length - n) {
                num1S += this.s[i];
            } else {
                num2S += this.s[i];
            }
        }
        let num1BigInt = new BigInt(num1S);
        let num2BigInt = new BigInt(num2S);
        num1BigInt.setC(this.c + n);
        num2BigInt.setC(this.c);
        result.push(num1BigInt);
        result.push(num2BigInt);
        return result;
    }
}

class BigIntOperation {
    static add(num1, num2) {
        let bi1 = new BigInt(num1);
        let bi2 = new BigInt(num2);
        if (!bi1.u && !bi2.u) {
            // 负负
            return "-" + this.add(num1.substring(1, num1.length), num2.substring(1, num2.length));
        } else if (bi1.u && !bi2.u) {
            // 正负
            return this.subtract(num1, num2.substring(1, num2.length));
        } else if (!bi1.u && bi2.u) {
            // 负正
            return this.subtract(num2, num1.substring(1, num1.length));
        }
        let index = 0;
        let result = "";
        let jw = false;
        while (index <= bi1.s.length || index <= bi2.s.length) {
            let n1 = 0;
            let n2 = 0;
            if (index < bi1.s.length) {
                n1 = parseInt(bi1.s[index]);
            }
            if (index < bi2.s.length) {
                n2 = parseInt(bi2.s[index]);
            }
            let signSum = 0;
            if (jw) {
                signSum = n1 + n2 + 1;
            } else {
                signSum = n1 + n2;
            }
            Math.floor(signSum / 10) > 0 ? jw = true : jw = false;
            signSum = signSum % 10;
            result = signSum + result;
            index++;
        }
        return result.replace(/\b(0+)/gi, "");
    }

    static subtract(num1, num2) {
        let bi1 = new BigInt(num1);
        let bi2 = new BigInt(num2);
        if (!bi1.u && bi2.u) {
            // 负正 -> -(num1+num2)
            return "-" + this.add(num1.substring(1, num1.length), num2);
        } else if (bi1.u && bi2.u) {
            // 正正
            if (bi1.lt(bi2)) {
                // 被减数比减数小
                return "-" + this.subtract(num2, num1);
            }
        } else if (bi1.u && !bi2.u) {
            // 正负 -> (num1+num2)
            return this.add(num1, num2.substring(1, num2.length));
        } else if (!bi1.u && !bi2.u) {
            return this.subtract(num2.substring(1, num2.length), num1.substring(1, num1.length));
        }
        let index = 0;
        let result = "";
        let jw = false;
        while (index <= bi1.s.length || index <= bi2.s.length) {
            let n1 = 0;
            let n2 = 0;
            if (index < bi1.s.length) {
                n1 = parseInt(bi1.s[index]);
            }
            if (index < bi2.s.length) {
                n2 = parseInt(bi2.s[index]);
            }
            let signSum = 0;
            if (jw) {
                signSum = n1 - 1 - n2;
            } else {
                signSum = n1 - n2;
            }
            signSum < 0 ? jw = true : jw = false;
            signSum = jw ? signSum += 10 : signSum;
            result = signSum + result;
            index++;
        }
        return result.replace(/\b(0+)/gi, "");
    }

    static multiply(num1, num2) {
        let num1BigInt = new BigInt(num1);
        let num2BigInt = new BigInt(num2);
        let result = this.doMultiply(num1BigInt, num2BigInt);
        let fNum = 0;
        if (!num1BigInt.u) {
            fNum++;
        }
        if (!num2BigInt.u) {
            fNum++;
        }
        let fh = fNum % 2 === 0 ? "" : "-";
        return fh + result;
    }

    static doMultiply(num1BigInt, num2BigInt) {
        if (num1BigInt.s.length === 1 && num2BigInt.s.length === 1) {
            let str = "" + (parseInt(num1BigInt.toStringValue()) * parseInt(num2BigInt.toStringValue()));
            let result = new BigInt(str);
            result.setC(num1BigInt.c + num2BigInt.c);
            return result.toZeroC().toStringValue();
        } else if (num1BigInt.s.length > 1 && num2BigInt.s.length > 1) {
            let result1 = num1BigInt.toSplit(Math.floor(num1BigInt.s.length / 2));
            let result2 = num2BigInt.toSplit(Math.floor(num2BigInt.s.length / 2));
            let n1 = result1[0];
            let n2 = result1[1];
            let n3 = result2[0];
            let n4 = result2[1];
            let n13 = this.doMultiply(n1, n3);
            let n14 = this.doMultiply(n1, n4);
            let n23 = this.doMultiply(n2, n3);
            let n24 = this.doMultiply(n2, n4);
            let n1314 = this.add(n13, n14);
            let n131423 = this.add(n1314, n23);
            let n13142324 = this.add(n131423, n24);
            return n13142324;
        } else if (num1BigInt.s.length > 1 || num2BigInt.s.length > 1) {
            let a = num1BigInt;
            let b = num2BigInt;
            if (num2BigInt.s.length > 1) {
                a = num2BigInt;
                b = num1BigInt;
            }
            let result1 = a.toSplit(Math.floor(a.s.length / 2));
            let n1 = result1[0];
            let n2 = result1[1];
            let n13 = this.doMultiply(n1, b);
            let n14 = this.doMultiply(n2, b);
            let n1314 = this.add(n13, n14);
            return n1314;
        }
        return "";
    }

    static generationBigInt(s) {
        return new BigInt(s);
    }
}

export default BigIntOperation;