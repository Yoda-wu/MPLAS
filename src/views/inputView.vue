<template>

  <div>
    <div>
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
      <a> 
        <el-upload
        class="upload-demo"
        drag
        action=""
        :on-change="fileChange"
        :http-request="submit"	
        :file-list="fileList"
        multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传json文件</div>
      </el-upload>
     </a>
    </div>
    <el-tabs v-model="activeName" @tab-click="handleSelectMenu">
      <el-tab-pane label="AST" name="first" index="ast"></el-tab-pane>
      <el-tab-pane label="CFG" name="second" index="cfg"></el-tab-pane>
      <el-tab-pane label="DDG" name="third" index="ddg"></el-tab-pane>
      <el-tab-pane label="PDG" name="fourth" index="pdg"></el-tab-pane>
      <router-view></router-view>
    </el-tabs>
  </div>
  
    
  </template>
  
  <script>
  import axios from 'axios';
  

  let fileObj;
  
  export default {
      computed: {
          user() {
              return this.$store.getters.name;
          },
      },
      data() {
          return {
              activeName: "first",
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
              fileList:[],
              ast:{},
          };
      },
      methods: {
          handleSelectMenu(item) {
              console.log(item.$attrs.index,this.ast);
              let _ts = this;
              _ts.$router.push({
                  name: `${item.$attrs.index}`,
                  params:{data:this.ast},
              });
          },
          fileChange(e){
            console.log(e);
            fileObj=e;
          },

        async submit(){
            let formData=new FormData();
            formData.append("lang","java");
            formData.append("file",fileObj);
            console.log(fileObj,formData);
            axios.post("https://mock.apifox.cn/m1/2527665-0-default/ast",formData).then(res=>{
              console.log(res.data);
              this.ast=res.data.data;
            });
        }
    },
  
  };
  </script>
  
  <style>
  </style>
  
  