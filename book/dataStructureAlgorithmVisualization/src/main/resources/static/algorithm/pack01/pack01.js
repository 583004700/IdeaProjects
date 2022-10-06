class Pack01{
    constructor(wArr,vArr) {
        this.w = wArr;
        this.v = vArr;
        this.cache = {};
        this.gj = {};
    }

    // 递归解法
    f(i, j) {
        let gj = this.gj;
        let cache = this.cache;
        let w = this.w;
        let v = this.v;
        let key = i + "," + j;
        if (cache[key] !== undefined) {
            //console.log("从缓存中获取数据！：" + cache[key]);
            return cache[key];
        }
        if (i === 0) {
            let r = 0;
            if (w[i] <= j) {
                r = v[0];

                if(gj[j] === undefined){
                    gj[j] = [];
                }
                gj[j].push(i);

            }
            cache[key] = r;
            return r;
        } else {
            if (w[i] <= j) {
                let a1 = this.f(i - 1, j);
                let a2 = this.f(i - 1, j - w[i]) + v[i];
                let r = Math.max(a1, a2);
                if (a1 < a2) {
                    if (gj[j - w[i]] === undefined) {
                        gj[j - w[i]] = [];
                    }
                    gj[j] = [...gj[j - w[i]],i];
                }
                cache[key] = r;
                return r;
            } else {
                let a1 = this.f(i - 1, j);
                cache[key] = a1;
                return a1;
            }
        }
    }
}

export default Pack01;