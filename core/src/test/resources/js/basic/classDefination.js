class Runoob {
    constructor(name) {
        this.sitename = name;
    }

    get s_name() {
        return this.sitename;
    }

    set s_name(x) {
        this.sitename = x;
    }
}

let noob = new Runoob("菜鸟教程");

document.getElementById("demo").innerHTML = noob.s_name;