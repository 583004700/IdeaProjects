class ObjectExtend{
    static exec(){
        Object.prototype.get = function (key) {
            return this[key];
        }

        Object.prototype.put = function (key, value) {
            this[key] = value;
        }

        Object.prototype.containsKey = function (key) {
            return typeof this.get(key) === "undefined";
        }
    }
}

export default ObjectExtend;