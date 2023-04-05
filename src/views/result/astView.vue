<template>
    <div>
        <el-container  style="height: 700px; border: 1px solid #eee">
        <el-header  style="font-size:40px;background-color: rgb(238, 241, 246)">多语言代码分析系统</el-header>
        <el-container>

        <el-aside width="200px" style="border: 1px solid #eee; background-color: rgb(238, 241, 246)">
            <el-menu :default-openeds="['1', '3']">
            <el-submenu index="1">
                <template slot="title"><i class="el-icon-message">
                </i><router-link to="/input">代码输入</router-link></template>
            </el-submenu>
            <el-submenu index="2">
                <template slot="title"><i class="el-icon-message"></i>图形化</template>
                <el-menu-item index="1-1">
                    <router-link to="/ast">AST</router-link>
                </el-menu-item>
                <el-menu-item index="1-2">
                    <router-link to="/cfg">CFG</router-link>
                </el-menu-item>
                <el-menu-item index="1-3">
                    <router-link to="/ddg">DDG</router-link>
                </el-menu-item>
                <el-menu-item index="1-4">
                    <router-link to="/pdg">PDG</router-link>
                </el-menu-item>
            </el-submenu>
            </el-menu>
        </el-aside>

        <el-main>
            <canvas height="1000" width="4000" id="canvas"></canvas>
        </el-main>

        </el-container>
        </el-container>
    </div>
</template>



<script>
import {drawAST} from "../js/ast.js";

export default{
    data(){
        return{

        }
    },
    methods: {
        receive(){

        const data=this.$route.query.data;
                    
        let cs = document.getElementById('canvas');
            
        let cxt = cs.getContext('2d');

        cxt.fillText(data, 10, 10);

        drawAST();
        }

    
},
mounted() {
    this.receive();
},
};

/* var canvas = document.getElementById('canvas');
canvas.height = 500;
canvas.width = 1000; */
</script>