class NumberExtend{
    static exec(){
        Number.prototype.compareTo = function (other) {
            if (this < other) {
                return -1;
            } else if (this > other) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}

export default NumberExtend;