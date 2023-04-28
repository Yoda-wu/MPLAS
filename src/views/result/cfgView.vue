<template>
    <div>
        <el-container  style="height: 700px; border: 1px solid #eee">
            
            <el-container>
                
                <el-main>
                    <canvas height="4000" width="1200" id="canvas"></canvas>
                </el-main>

            </el-container>
        </el-container>
    </div>
    
</template>

<script>
import { drawFun } from '../js/cfg';
export default{
    data(){
        return{
            
        }
    },


    mounted() {
        console.log("cfgView");
        let node=this.$route.params.data //123;
        console.log(node.data);
        drawFun(0,0,10,node.data);
    },

    methods: {

    }
}
</script>

<style>

</style>