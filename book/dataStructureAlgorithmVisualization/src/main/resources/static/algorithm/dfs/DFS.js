class DFS{
    constructor() {
        // 列数
        this.colNum = 10;
        // 行数
        this.rowNum = 7;
        // 障碍物
        this.barrier = [{row:1,col:2},{row:2,col:2},{row:3,col:2},{row:4,col:2},{row:5,col:2},
            {row:5,col:3},{row:5,col:4},{row:5,col:5},{row:5,col:6},{row:5,col:7},{row:5,col:8},{row:4,col:8},
            {row:3,col:8},{row:3,col:7},{row:3,col:6},{row:3,col:5},{row:3,col:4}
        ];
        // 障碍物颜色
        this.barrierColor = "black";
        //目标位置
        this.dist = {row:4,col:7};
        // 目标位置的颜色
        this.distColor = "green";
        // 人物位置
        this.person = {row:1,col:1};
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

    startGo(){
        this.goEd = [];
        this.stepIndex = 0;
        let result = this.go(this.person.row,this.person.col);
        return result;
    }

    go(row,col){
        if(this.canGo(row,col)){
            let that = this;
            that.setPersonPosition(row,col);
            let current = {row:that.person.row,col:that.person.col};
            that.goEd.push(current);
            if(row === this.dist.row && col === this.dist.col){
                return true;
            }
            // 上
            let up = {row:that.person.row-1,col:that.person.col};
            if(that.go(up.row, up.col)){
                //到达目的就不再尝试
                return true;
            }
            // 右
            let right = {row:that.person.row,col:that.person.col+1};
            if(that.go(right.row, right.col)){
                return true;
            }
            // 下
            let down = {row:that.person.row+1,col:that.person.col};
            if(that.go(down.row, down.col)){
                return true;
            }
            // 左
            let left = {row:that.person.row,col:that.person.col-1};
            if(that.go(left.row, left.col)){
                return true;
            }
            if(current !== this.goEd[0]) {
                that.goEd.remove(current);
                let pre = {row: this.goEd[this.goEd.length - 1].row, col: this.goEd[this.goEd.length - 1].col};
                this.setPersonPosition(pre.row, pre.col);
            }
        }
        return false;
    }

    // 判断坐标能否走
    canGo(row,col){
        if(this.isBarrier(row,col)){
            // 如果是障碍物
            return false;
        }
        if(row >= this.rowNum || row < 0 || col >= this.colNum || col < 0){
            // 如果超出边界
            return false;
        }
        for (let i = 0; i < this.goEd.length; i++) {
            let l = this.goEd[i];
            if(l.row === row && l.col === col){
                // 如果已经走过
                return false;
            }
        }
        return true;
    }

    // 添加障碍物
    addBarrier(row,col){
        let e = this.getLiEle(row,col);
        if(!e){
            alert("坐标不在范围内！");
            return;
        }
        if(this.isBarrier(row,col)){
            alert("障碍物已经存在，请不要重复设置！");
            return;
        }
        if(row === this.person.row && col === this.person.col){
            alert("障碍物不能在人物上！");
            return;
        }
        if(row === this.dist.row && col === this.dist.col){
            alert("障碍物不能在终点！");
            return;
        }
        this.barrier.push({row,col});
        this.getLiEle(row,col).style.backgroundColor = this.barrierColor;
    }
    // 删除障碍物
    deleteBarrier(row,col){
        for (let i = 0; i < this.barrier.length; i++) {
            let l = this.barrier[i];
            if(l.row === row && l.col === col){
                this.barrier.removeIndex(i);
                this.getLiEle(row,col).style.backgroundColor = this.spaceColor;
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
    isBarrier(row,col){
        let e = this.getLiEle(row,col);
        if(!e){
            return false;
        }
        if(e.style.backgroundColor === this.barrierColor){
            return true;
        }
        return false;
    }

    // 通过row和col获取li元素
    getLiEle(row,col){
        let index = this.colNum * row + col;
        if(index >= 0 && index < this.liEles.length){
            return this.liEles[index];
        }
        return null;
    }

    initGraph(){
        let ulEle = document.getElementById("graphUl");
        let that = this;
        if(this.liEles.length === 0) {
            for (let i = 0; i < this.count; i++) {
                let liEle = document.createElement("li");
                let row = Math.floor(i / 10);
                let col = i % 10;
                liEle.setAttribute("onclick", "toggleBarrier(" + row + "," + col + ")");
                ulEle.appendChild(liEle);
            }
        }
        this.liEles = document.getElementsByTagName("li");
    }

    toggleBarrier(row,col){
        if(this.finish) {
            if (!this.deleteBarrier(row, col)) {
                this.addBarrier(row, col);
            }
        }
    }

    initBarrier(){
        // 设置障碍物
        for (let i = 0; i < this.barrier.length; i++) {
            let b = this.barrier[i];
            let e = this.getLiEle(b.row,b.col);
            e.style.backgroundColor = this.barrierColor;
        }
    }

    // 设置人物位置
    setPersonPosition(row,col){
        this.person = {row:row,col:col};
    }

    setPersonPositionAndDraw(row,col){
        this.person = {row:row,col:col};
        let personEle = this.getLiEle(row,col);
        if(personEle) {
            this.personRealEle = document.getElementById("person");
            this.personRealEle.style.left = personEle.offsetLeft+"px";
            this.personRealEle.style.top = personEle.offsetTop+"px";
        }
    }

    play(callback){
        let that = this;
        let i = this.stepIndex;
        setTimeout(function(){
            that.setPersonPositionAndDraw(that.goEd[i].row,that.goEd[i].col);
            that.stepIndex++;
            if(that.stepIndex<that.goEd.length){
                that.play(callback);
            }else{
                this.finish = true;
                callback();
            }
        },1000);
    }

    initDist(){
        let distEle = this.getLiEle(this.dist.row,this.dist.col);
        if(distEle){
            distEle.style.backgroundColor = this.distColor;
        }
    }

    init(){
        this.initGraph();
        this.initBarrier();
        this.initDist();
        this.setPersonPositionAndDraw(this.person.row,this.person.col);
    }
}

export default DFS;