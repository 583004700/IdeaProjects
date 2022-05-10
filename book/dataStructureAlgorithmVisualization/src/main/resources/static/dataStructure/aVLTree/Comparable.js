class Comparable {
    compareTo(other) {

    }
}

Number.prototype.compareTo = function (other) {
    if (this < other) {
        return -1;
    } else if (this > other) {
        return 1;
    } else {
        return 0;
    }
}