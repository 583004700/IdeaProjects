class BFS {
    constructor() {
        // 列数
        this.colNum = 10;
        // 行数
        this.rowNum = 7;
        // 障碍物
        this.barrier = [{row: 1, col: 2}, {row: 2, col: 2}, {row: 3, col: 2}, {row: 4, col: 2}, {row: 5, col: 2},
            {row: 5, col: 3}, {row: 5, col: 4}, {row: 5, col: 5}, {row: 5, col: 6}, {row: 5, col: 7}, {
                row: 5,
                col: 8
            }, {row: 4, col: 8},
            {row: 3, col: 8}, {row: 3, col: 7}, {row: 3, col: 6}, {row: 3, col: 5}, {row: 3, col: 4}
        ];
        // 障碍物颜色
        this.barrierColor = "black";
        //目标位置
        this.dist = {row: 4, col: 3};
        // 目标位置的颜色
        this.distColor = "green";
        // 人物位置
        this.person = {row: 1, col: 1};
        // 人物颜色
        this.personColor = "red";
        // 空地颜色
        this.spaceColor = "pink";
        this.liEles = [];
        // 已经走过的点
        this.goEd = [];
        this.stepIndex = 0;
        this.count = 70;
        this.finish = true;
    }

    startGo(callback) {
        this.stepIndex = 0;
        this.goEd = [];
        this.goEd.push({row: this.person.row, col: this.person.col, index: 0});
        this.go(this.person.row, this.person.col,callback);
    }

    go(row, col,callback) {
        let queueIndex = 0;
        let that = this;
        let result = false;
        f();
        function f() {
            if (queueIndex < that.goEd.length && !result) {
                setTimeout(f,1000);
                let current = that.goEd[queueIndex];
                let currentLiEle = that.getLiEle(current.row,current.col);
                let preCurrent = queueIndex - 1 >= 0 && queueIndex - 1 < that.goEd.length ? that.goEd[queueIndex - 1] : null;
                if(preCurrent != null){
                    that.getLiEle(preCurrent.row,preCurrent.col).style.backgroundColor = that.spaceColor;
                }
                currentLiEle.style.backgroundColor = "orange";
                let index = current.index;
                // 上
                let up = {row: current.row - 1, col: current.col};
                up.pre = current;
                // 右
                let right = {row: current.row, col: current.col + 1};
                right.pre = current;
                // 下
                let down = {row: current.row + 1, col: current.col};
                down.pre = current;
                // 左
                let left = {row: current.row, col: current.col - 1};
                left.pre = current;
                setTimeout(function(){
                    function opt(o) {
                        if (o.row === that.dist.row && o.col === that.dist.col) {
                            that.goEd = [];
                            that.goEd.push(o);
                            while (o.pre) {
                                that.goEd.unshift(o.pre);
                                o = o.pre;
                            }
                            result = true;
                            callback(true);
                        }else if (that.canGo(o.row, o.col)) {
                            that.goEd.push(o);
                            o.index = index + 1;
                            that.getLiEle(o.row, o.col).innerHTML = o.index;
                        }
                    }
                    opt(up);
                    opt(right);
                    opt(down);
                    opt(left);
                },200);
                queueIndex++;
            } else {
                if(!result) {
                    callback(false);
                }
            }
        }
    }

    // 判断坐标能否走
    canGo(row, col) {
        if (this.isBarrier(row, col)) {
            // 如果是障碍物
            return false;
        }
        if (row >= this.rowNum || row < 0 || col >= this.colNum || col < 0) {
            // 如果超出边界
            return false;
        }
        for (let i = 0; i < this.goEd.length; i++) {
            let l = this.goEd[i];
            if (l.row === row && l.col === col) {
                // 如果已经走过
                return false;
            }
        }
        return true;
    }

    // 添加障碍物
    addBarrier(row, col) {
        let e = this.getLiEle(row, col);
        if (!e) {
            alert("坐标不在范围内！");
            return;
        }
        if (this.isBarrier(row, col)) {
            alert("障碍物已经存在，请不要重复设置！");
            return;
        }
        if (row === this.person.row && col === this.person.col) {
            alert("障碍物不能在人物上！");
            return;
        }
        if (row === this.dist.row && col === this.dist.col) {
            alert("障碍物不能在终点！");
            return;
        }
        this.barrier.push({row, col});
        this.init();
    }

    // 删除障碍物
    deleteBarrier(row, col) {
        for (let i = 0; i < this.barrier.length; i++) {
            let l = this.barrier[i];
            if (l.row === row && l.col === col) {
                this.barrier.removeIndex(i);
                this.init();
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是障碍物
     * @param row
     * @param col
     */
    isBarrier(row, col) {
        let e = this.getLiEle(row, col);
        if (!e) {
            return false;
        }
        if (e.style.backgroundColor === this.barrierColor) {
            return true;
        }
        return false;
    }

    // 通过row和col获取li元素
    getLiEle(row, col) {
        let index = this.colNum * row + col;
        if (index >= 0 && index < this.liEles.length) {
            return this.liEles[index];
        }
        return null;
    }

    initGraph() {
        let ulEle = document.getElementById("graphUl");
        let that = this;
        ulEle.innerHTML = "";
        for (let i = 0; i < this.count; i++) {
            let liEle = document.createElement("li");
            let row = Math.floor(i / 10);
            let col = i % 10;
            liEle.style.backgroundColor = this.spaceColor;
            liEle.setAttribute("onclick", "toggleBarrier(" + row + "," + col + ")");
            ulEle.appendChild(liEle);
        }
        this.liEles = document.getElementsByTagName("li");
    }

    toggleBarrier(row, col) {
        if (this.finish) {
            if (!this.deleteBarrier(row, col)) {
                this.addBarrier(row, col);
            }
        }
    }

    initBarrier() {
        // 设置障碍物
        for (let i = 0; i < this.barrier.length; i++) {
            let b = this.barrier[i];
            let e = this.getLiEle(b.row, b.col);
            e.style.backgroundColor = this.barrierColor;
        }
    }

    // 设置人物位置
    setPersonPosition(row, col) {
        this.person = {row: row, col: col};
    }

    setPersonPositionAndDraw(row, col) {
        this.person = {row: row, col: col};
        let personEle = this.getLiEle(row, col);
        if (personEle) {
            this.personRealEle = document.getElementById("person");
            this.personRealEle.style.left = personEle.offsetLeft + "px";
            this.personRealEle.style.top = personEle.offsetTop + "px";
        }
    }

    play(callback) {
        let that = this;
        let i = this.stepIndex;
        setTimeout(function () {
            that.setPersonPositionAndDraw(that.goEd[i].row, that.goEd[i].col);
            that.stepIndex++;
            if (that.stepIndex < that.goEd.length) {
                that.play(callback);
            } else {
                that.finish = true;
                callback();
            }
        }, 1000);
    }

    initDist() {
        let distEle = this.getLiEle(this.dist.row, this.dist.col);
        if (distEle) {
            distEle.style.backgroundColor = this.distColor;
        }
    }

    init() {
        this.initGraph();
        this.initBarrier();
        this.initDist();
        this.setPersonPositionAndDraw(this.person.row, this.person.col);
    }
}

export default BFS;