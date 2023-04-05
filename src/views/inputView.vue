<template>
    <div>
        <el-container  style="height: 700px; border: 1px solid #eee">
        <el-header  style="font-size:40px;background-color: rgb(238, 241, 246)">多语言代码分析系统</el-header>
        <el-container>

        <el-aside width="200px" style="border: 1px solid #eee; background-color: rgb(238, 241, 246)">
            <el-menu :default-openeds="['1', '3']">
            <el-submenu index="1">
                <template slot="title"><i class="el-icon-message"></i>代码输入</template>
            </el-submenu>
            <el-submenu index="2">
                <template slot="title"><i class="el-icon-message"></i>图形化</template>
                <el-menu-item index="1-1">
                    <router-link to="/AST">AST</router-link>
                </el-menu-item>
                <el-menu-item index="1-2">
                    <router-link to="/CFG">CFG</router-link>
                </el-menu-item>
                <el-menu-item index="1-3">
                    <router-link to="/DDG">DDG</router-link>
                </el-menu-item>
                <el-menu-item index="1-4">
                    <router-link to="/PDG">PDG</router-link>
                </el-menu-item>
            </el-submenu>
            </el-menu>
        </el-aside>

        <el-main>
            <!-- <el-input
                type="textarea"
                :rows="10"
                placeholder="请输入内容"
                v-model="textarea">
            </el-input>

            <br>

            <el-row>
                <el-button type="primary" round
              style="margin-right: 10px">提交</el-button>
            </el-row> -->
            


            <p>
                <el-select v-model="value" placeholder="请选择">
                <el-option
                v-for="item in options"
                :key="item.label"
                :label="item.label"
                :value="item.value">
                </el-option>
            </el-select>
            </p>


            <!--  https://jsonplaceholder.typicode.com/posts/ -->
            <!-- :on-exceed="handleExceed" -->
            <el-upload
                class="upload-demo"
                drag
                action=""
                multiple
                :on-success="upSuccess"
                :on-error="upError"
                :on-change="uploadFile"
                :file-list="fileList"
                :limit="1">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <!-- <div class="el-upload__tip" slot="tip">只能上传.cpp/.py文件</div> -->
            </el-upload>

            <el-row>
                <el-button type="primary" round @click="add">主要按钮</el-button>
            </el-row>
        </el-main>

        </el-container>
        </el-container>
    </div>
</template>

<script>
import axios from 'axios';
//import { Scope } from 'eslint-scope';
//import { Result } from 'element-ui';

export default{
    data(){
        return{
            textarea: '',
            options: [{
                value: '1',
                label: 'Java'
                }, {
                value: '2',
                label: 'C++'
                }, {
                value: '3',
                label: 'Rudy'
                }, {
                value: '4',
                label: 'JavaScript'
                }],
            value: '1',
            fileList:[]

        }
    },
    methods: {
        add(){
            const data=this.value;
            this.$router.push({path:"/AST",query:{data}})

        },


      // 上传成功
      uploadFile (item) {
                if (item.status !== 'ready') return;
                let formData = new FormData()   //将文件转为FormData格式
                let file = item.raw
                formData.append('file', file)
                axios({
                    url: 'http://localhost:8081/feedback/fileList', //后端提供的接口
                    method: 'post',
                    data: formData,
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
            },
            upSuccess(res) {
                this.$message({
                    type: 'success',
                    message: '上传成功',
                    showClose: true,
                    offset: 80,
                })
            },
            // 上传失败
            upError() {
                this.$message({
                    type: 'error',
                    message: '上传失败',
                    showClose: true,
                    offset: 80,
                });
            },
            handleExceed(files, fileList) {
                this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
            },
            beforeRemove(file, fileList) {
                return this.$confirm(`确定移除 ${ file.name }？`);
            }
    },
    mounted() {
       axios.post("http://yapi.smart-xwork.cn/mock/169327/emp/list",this.value).then((result)=>{
            this.tableData=result.data.data;
       });
    },
    /* mounted() {
       axios.get("http://yapi.smart-xwork.cn/mock/169327/emp/list").then((result)=>{
            this.tableData=result.data.data;
       });
    } */
    
}
</script>

<style>

</style>