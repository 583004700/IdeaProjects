import BigIntOperation from "../bigInt/bigint";

class BigFloatOperation {
    /**
     * 拆分成整数部分和小数部分
     * @param number
     */
    static splitNumber(number) {
        let numberArr = number.split(".");
        let numberInt = numberArr[0];
        let numberFloat = "0";
        if (numberArr.length > 1) {
            numberFloat = numberArr[1];
        }
        return {numberInt: numberInt.replaceAll(".", ""), numberFloat: numberFloat};
    }

    static isFs(s) {
        return s.charAt(0) === "-";
    }

    static add(float1Value, float2Value) {
        let float1ValueSplit = this.splitNumber(float1Value);
        let float2ValueSplit = this.splitNumber(float2Value);
        let f1Float = float1ValueSplit.numberFloat;
        let f2Float = float2ValueSplit.numberFloat;
        let fs = Math.abs(f1Float.length - f2Float.length);
        let addZero = null;
        if (f1Float.length < f2Float.length) {
            addZero = float1ValueSplit;
        } else if (f1Float.length > f2Float.length) {
            addZero = float2ValueSplit;
        }
        if (addZero != null) {
            for (let i = 0; i < fs; i++) {
                addZero.numberFloat += "0";
            }
        }
        f1Float = float1ValueSplit.numberFloat;
        f2Float = float2ValueSplit.numberFloat;
        let noDot = BigIntOperation.add(float1ValueSplit.numberInt + f1Float, float2ValueSplit.numberInt + f2Float);
        let noDotArr = noDot.split("");
        let result = "";
        for (let i = noDotArr.length - 1, o = 0; i >= 0 || o <= float2ValueSplit.numberFloat.length; i--, o++) {
            if (o === float2ValueSplit.numberFloat.length) {
                result = "." + result;
            }
            if (i < noDotArr.length && i >= 0 && noDotArr[i] !== '-') {
                result = noDotArr[i] + result;
            } else {
                result = "0" + result;
            }
        }
        if (result.charAt(0) === '0' && result.indexOf(".") === -1) {
            result = "0." + result;
        }
        if (result.indexOf(".") !== -1) {
            result = result.replace(/(0+)$/gi, "");
        }
        if (result.charAt(1) !== '.') {
            result = result.replace(/^(0+)/gi, "");
        }
        result = result.replace(/^(\.)/gi, "");
        result = result.replace(/(\.)$/gi, "");
        if (this.isFs(noDot)) {
            result = "-" + result;
        }
        return result;
    }

    static subtract(float1Value, float2Value) {
        let addNum = this.isFs(float2Value) ? float2Value.substring(1, float2Value.length) : "-" + float2Value;
        return this.add(float1Value, addNum);
    }

    static multiply(float1Value, float2Value) {
        let float1ValueSplit = this.splitNumber(float1Value);
        let float2ValueSplit = this.splitNumber(float2Value);
        let f1Float = float1ValueSplit.numberFloat;
        let f2Float = float2ValueSplit.numberFloat;
        let allFloatLength = f1Float.length + f2Float.length;
        let noDot = BigIntOperation.multiply(float1ValueSplit.numberInt + f1Float, float2ValueSplit.numberInt + f2Float);
        let noDotArr = noDot.split("");
        let result = "";
        for (let i = noDotArr.length - 1, o = 0; i >= 0 || o <= allFloatLength; i--, o++) {
            if (o === allFloatLength) {
                result = "." + result;
            }
            if (i < noDotArr.length && i >= 0 && noDotArr[i] !== '-') {
                result = noDotArr[i] + result;
            } else {
                result = "0" + result;
            }
        }
        if (result.charAt(0) === '0' && result.indexOf(".") === -1) {
            result = "0." + result;
        }
        if (result.indexOf(".") !== -1) {
            result = result.replace(/(0+)$/gi, "");
        }
        if (result.charAt(1) !== '.') {
            result = result.replace(/^(0+)/gi, "");
        }
        result = result.replace(/^(\.)/gi, "");
        result = result.replace(/(\.)$/gi, "");
        if (this.isFs(noDot)) {
            result = "-" + result;
        }
        return result;
    }
}

export default BigFloatOperation;