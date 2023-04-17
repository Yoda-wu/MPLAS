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
                    <router-link :to="{name:'ast',params:{ast}}">AST</router-link>
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
            

            <el-upload
                class="upload-demo"
                ref="upload"
                action
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-change="handleChange"
                :http-request="uploadFile"		
                :file-list="fileList"
                :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
                
            </el-upload>


            


            <!--  https://jsonplaceholder.typicode.com/posts/ -->
            <!-- :on-exceed="handleExceed" -->
            <!-- <el-upload
                class="upload-demo"
                drag
                action=""
                multiple
                :on-success="upSuccess"
                :on-error="upError"
                :on-change="uploadFile"
                :file-list="fileList"
                :limit="3">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传.cpp/.py文件</div>
            </el-upload>

            <el-row>
                <el-button type="primary" round @click="test">主要按钮</el-button>
            </el-row> -->
        </el-main>

        </el-container>
        </el-container>
    </div>
</template>

<script>
import axios from 'axios';
//import { Scope } from 'eslint-scope';
//import { Result } from 'element-ui';
//var axios = require('axios');




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
            //fileList:[],
            //file:'',
            //fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
            //fileList:[],
            //ast:{"directed":true,"label":"AST of Test1.java","type":"Abstract Syntax Tree (AST)","file":"Test1.java","nodes":[{"id":0,"line":0,"type":"ROOT","label":"Test1.java","normalized":"Test1.java"},{"id":1,"line":2,"type":"CLASS","label":"CLASS","normalized":"CLASS"},{"id":2,"line":2,"type":"MODIFIER","label":"public","normalized":"public"},{"id":3,"line":2,"type":"NAME","label":"Test1","normalized":"Test1"},{"id":4,"line":4,"type":"METHOD","label":"METHOD","normalized":"METHOD"},{"id":5,"line":4,"type":"MODIFIER","label":"public static","normalized":"public static"},{"id":6,"line":4,"type":"RETURN","label":"void","normalized":"void"},{"id":7,"line":4,"type":"NAME","label":"main","normalized":"$METHOD_1"},{"id":8,"line":4,"type":"PARAMS","label":"PARAMS","normalized":"PARAMS"},{"id":9,"line":4,"type":"VAR","label":"VAR","normalized":"VAR"},{"id":10,"line":4,"type":"TYPE","label":"String[]","normalized":"String[]"},{"id":11,"line":4,"type":"NAME","label":"args","normalized":"$VARL_1"},{"id":12,"line":4,"type":"BLOCK","label":"BLOCK","normalized":"BLOCK"},{"id":13,"line":5,"type":"VAR","label":"VAR","normalized":"VAR"},{"id":14,"line":5,"type":"TYPE","label":"int","normalized":"int"},{"id":15,"line":5,"type":"NAME","label":"i","normalized":"$VARL_2"},{"id":16,"line":5,"type":"INIT","label":"= 0","normalized":"= 0"},{"id":17,"line":6,"type":"","label":"i++","normalized":"$VARL_2++"},{"id":18,"line":7,"type":"IF","label":"IF","normalized":"IF"},{"id":19,"line":7,"type":"COND","label":"i > 0","normalized":"$VARL_2 > 0"},{"id":20,"line":7,"type":"THEN","label":"THEN","normalized":"THEN"},{"id":21,"line":8,"type":"","label":"System.out.println('Positive')","normalized":"System.out.println('Positive')"},{"id":22,"line":9,"type":"","label":"i *= 2","normalized":"$VARL_2 ?= 2"},{"id":23,"line":10,"type":"ELSE","label":"ELSE","normalized":"ELSE"},{"id":24,"line":11,"type":"","label":"System.out.println('Non-positive')","normalized":"System.out.println('Non-positive')"},{"id":25,"line":12,"type":"","label":"i *= -2","normalized":"$VARL_2 ?= -2"},{"id":26,"line":14,"type":"","label":"System.out.println('End')","normalized":"System.out.println('End')"}],
            //"edges":[{"id":0,"source":0,"target":1,"label":""},{"id":1,"source":1,"target":2,"label":""},{"id":2,"source":1,"target":3,"label":""},{"id":3,"source":1,"target":4,"label":""},{"id":4,"source":4,"target":5,"label":""},{"id":5,"source":4,"target":6,"label":""},{"id":6,"source":4,"target":7,"label":""},{"id":7,"source":4,"target":8,"label":""},{"id":8,"source":8,"target":9,"label":""},{"id":9,"source":9,"target":10,"label":""},{"id":10,"source":9,"target":11,"label":""},{"id":11,"source":4,"target":12,"label":""},{"id":12,"source":12,"target":13,"label":""},{"id":13,"source":13,"target":14,"label":""},{"id":14,"source":13,"target":15,"label":""},{"id":15,"source":13,"target":16,"label":""},{"id":16,"source":12,"target":17,"label":""},{"id":17,"source":12,"target":18,"label":""},{"id":18,"source":18,"target":19,"label":""},{"id":19,"source":18,"target":20,"label":""},{"id":20,"source":20,"target":21,"label":""},{"id":21,"source":20,"target":22,"label":""},{"id":22,"source":18,"target":23,"label":""},{"id":23,"source":23,"target":24,"label":""},{"id":24,"source":23,"target":25,"label":""},{"id":25,"source":12,"target":26,"label":""}]},
            ast:{},
            fileList: [],
           // 不支持多选
            multiple: false,
            formData: "",
        }
        },
    methods: {
        submitUpload() {
            this.$refs.upload.submit();
            
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      handleChange (file, fileList) {
        this.fileList = fileList;
        console.log(this.fileList, "sb");
      },
      uploadFile (item) {
            //var FormData = require('form-data');
            let formData = new FormData()
            console.log(item.file);
            formData.append("file", item.file);
            formData=item.file;
            console.log(formData);
            //data.append(this.fileList);
            //var file = item.raw
            //FormData.append('file', this.file)

            var config = {
            method: 'post',
            url: 'https://mock.apifox.cn/m1/2527665-0-default/ast',
            headers: { 
                'Accept': '*/*', 
                //...data.getHeaders()
            },
            data : formData
            //params:"Java"
            };
            

            axios(config).then(response=>{
            //console.log(JSON.stringify(response.data.data));
            //this.ast=response.data.data;
            //console.log(this.ast);
            if (response.data) {
            this.$notify({
              title: '成功',
              message: '提交成功',
              type: 'success',
              duration: 1000
            });
            this.ast=response.data;
            console.log(this.ast.data);
          }
            })
            .catch(function (error) {
            console.log(error);
            });
            
      //this.formData.append("file", file.file);
      
      },
        add(){
            const data=this.options[this.value-1].label;
            this.$router.push({path:"/AST",query:{data}})

        },

        //保存按钮
    onSubmit () {
        let formData = new FormData();
        formData.append("file", this.fileList[0].raw);//拿到存在fileList的文件存放到formData中
       //下面数据是我自己设置的数据,可自行添加数据到formData(使用键值对方式存储)
        //formData.append("title", this.title);
        axios.post("https://jsonplaceholder.typicode.com/posts/", formData, {
        "Content-Type": "multipart/form-data;charset=utf-8"
      })
        .then(res => {
          if (res.data) {
            this.$notify({
              title: '成功',
              message: '提交成功',
              type: 'success',
              duration: 1000
            });
            console.log(res.data);
          }
        })

       }

    },
    mounted() {
        
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