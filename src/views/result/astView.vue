<template>
    <div>
        <el-container  style="height: 700px; border: 1px solid #eee">
            
            <el-container>
                
                <el-main>
                    <canvas height="1000" width="2000" id="canvas"></canvas>
                </el-main>

            </el-container>
        </el-container>
    </div>
    
</template>

<script>
import { drawAST } from '../js/ast';
export default{
    data(){
        return{
            
        }
    },


    mounted() {
        console.log("astView");
        let node=this.$route.params.data //123;
        console.log(node.data);
        drawAST(node.data);
    },

    methods: {

    }
}
</script>

<style>

</style>