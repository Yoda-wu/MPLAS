<template>
    <div>
        <el-container  style="height: 700px; border: 1px solid #eee">
            
            <el-container>
                
                <el-main>
                    <canvas height="5000" width="1200" id="canvas"></canvas>
                </el-main>

            </el-container>
        </el-container>
    </div>
    
</template>

<script>
import { drawDDG } from '../js/ddg';
export default{
    data(){
        return{
            
        }
    },


    mounted() {
        console.log("ddgView");
        let node=this.$route.params.data //123;
        console.log(node.data);
        drawDDG(node.data);
    },

    methods: {

    }
}
</script>

<style>

</style>