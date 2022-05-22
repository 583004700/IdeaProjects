class ArrayExtend {
    static exec() {
        Array.prototype.remove = function (data) {
            this.forEach(function (item, index, arr) {
                if (item === data) {
                    arr.splice(index, 1);
                }
            });
        }

        Array.prototype.removeIndex = function (index) {
            let arr = this.splice(index, 1);
            if (arr.length > 0) {
                return arr[0];
            }
        }

        Array.prototype.add = function (data, index) {
            if (index || index === 0) {
                this.splice(index, 0, data);
            } else {
                this.push(data);
            }
        }

        Array.prototype.get = function (index) {
            return this[index];
        }

        Array.prototype.contains = function (data) {
            for (let i = 0; i < this.length; i++) {
                if (this[i] === data) {
                    return true;
                }
            }
            return false;
        }

        Array.prototype.addAll = function (data) {
            for (let i = 0; i < data.length; i++) {
                this.push(data[i]);
            }
            return this;
        }

        Array.prototype.size = function () {
            return this.length;
        }

        Array.prototype.contains = function (data) {
            for (let i = 0; i < this.length; i++) {
                let t = this[i];
                if (t === data) {
                    return true;
                }
            }
            return false;
        }

        Array.prototype.set = function (index, data) {
            this[index] = data;
        }
    }
}

export default ArrayExtend;