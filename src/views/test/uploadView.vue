<template>
    <div><!-- :http-request="submit"-->
        <div class="clearfix">
                <div class="box" style="border-right-style: solid;text-align: right;">

                        <el-select v-model="value" placeholder="请选择">
                            <el-option
                            v-for="item in options"
                            :key="item.label"
                            :label="item.label"
                            :value="item.value">
                            </el-option>
                        </el-select>

                        <el-input
                            type="textarea"
                            :rows="2"
                            placeholder="请输入函数"
                            :clearable="true"
                            :autosize="{ minRows: 13, maxRows: 13}"
                            v-model="textarea">
                        </el-input>
                        <el-button type="primary" round @click="submit">提交</el-button>

                </div>
                <div class="box">
                    <el-upload
                        class="upload-demo"
                        drag
                        action=""
                        accept=".json,.jsonl"
                        :file-list="fileList"	
                        :http-request="upload"
                        :on-change="handleChange"	
                        :on-preview="handlePreview"	
                        :on-remove="handleRemove"	
                        multiple>
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                        <div class="el-upload__tip" slot="tip">只允许上传.json/.jsonl文件，且不超过32M</div>
                    </el-upload>
                </div>
        </div>

        <p>
          <a></a>
        <el-button type="success" @click="down1">下载ast.json</el-button>
        <el-button type="success" @click="down2">下载cfg.json</el-button>
        <el-button type="success" @click="down3">下载ddg.json</el-button>
        <!-- <el-button type="success" @click="down">下载pdg.json</el-button> -->
        <el-button type="success" @click="down4">全部下载</el-button>

        </p>

        <el-tabs v-model="activeName" type="card" @tab-click="handleSelectMenu">
        <el-tab-pane label="AST" name="first" index="ast"></el-tab-pane>
        <el-tab-pane label="CFG" name="second" index="cfg"></el-tab-pane>
        <el-tab-pane label="DDG" name="third" index="ddg"></el-tab-pane>
        <!-- <el-tab-pane label="PDG" name="fourth" index="cfg"></el-tab-pane> -->
        <router-view></router-view>
  </el-tabs>
    </div>
  </template>

  <script>
  import axios from "axios";




  function download (url, name) {
  const a = document.createElement('a')
  a.download = name
  a.rel = 'noopener'
  a.href = url
  // 触发模拟点击
  a.dispatchEvent(new MouseEvent('click'))
  // 或者 a.click()
}


  export default{
    data(){
        return{
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
            u:0,
            ast:{},
            cfg:{},
            ddg:{},
            textarea:'',
        }
    },
    methods:{
      getAST(data){

          let lang=this.options[this.value-1].label;

          //formData.append("lang",lang);
          //console.log(this.fileList[0].raw);
          //formData.append("file",this.fileList[0].raw);

          var config = {
              method: 'post',
              url: 'https://mock.apifox.cn/m1/2527665-0-default/ast?lang='+lang,
              headers: { 
                
                  'Accept': '*/*', 
                  
              },
              data : data
            };
          
          if(this.u==0){
             axios(config).then((response)=> {
                  //console.log(JSON.stringify(response.data));
                  //this.ast=response.data;
  
                  if (response.data) {
                      this.ast=response.data;
                      console.log(this.ast.data);
                  }
              }).catch(error=>{
                  console.log(error);
                  this.$notify.error({
                  title: '错误',
                  message: 'ast获取error',
                  duration: 0
                  });
              }); 
          }
      },
      getCFG(data){

          let lang=this.options[this.value-1].label;


          var config = {
          method: 'post',
          url: 'https://mock.apifox.cn/m1/2527665-0-default/cfg?lang='+lang,
          headers: { 
            
              'Accept': '*/*', 
              
          },
          data : data
          };

          
          if(this.u==0){
              axios(config).then((response)=> {
                  //console.log(JSON.stringify(response.data));
                  //this.ast=response.data;
  
                  if (response.data) {
                      this.cfg=response.data;
                      console.log(this.cfg.data);
                  }
              }).catch(error=>{
                  console.log(error);
                  this.$notify.error({
                  title: '错误',
                  message: 'cfg获取error'
                  });
              });
          }
      },
      getDDG(data){

      let lang=this.options[this.value-1].label;


      var config = {
      method: 'post',
      url: 'https://mock.apifox.cn/m1/2527665-0-default/ddg?lang='+lang,
      headers: { 
        
          'Accept': '*/*', 
          
      },
      data : data
      };


      if(this.u==0){
          axios(config).then((response)=> {

              if (response.data) {
                  this.ddg=response.data;
                  console.log(this.ddg.data);
              }
          }).catch(error=>{
              console.log(error);
              this.$notify.error({
              title: '错误',
              message: 'ddg获取error'
              });
          });
      }
      },
        async upload(){
            var FormData = require('form-data');
            let formData=new FormData();

            let lang=this.options[this.value-1].label;

            //formData.append("lang",lang);
            console.log(this.fileList[0].raw);
            formData.append("file",this.fileList[0].raw);


            axios.all([this.getAST(formData),this.getCFG(formData),this.getDDG(formData)])
              .then(axios.spread((acct, perms) => {
                this.$notify({
                      title: '处理完成',
                      message: '请下拉在结果部分下载文件',
                      type: 'success',
                      duration: 0
                      });
              }));

        },
    //点击上传文件触发的额外事件,清空fileList
    delFile () {
      this.fileList = [];
    },
    handleChange (file, fileList) {
      this.fileList = fileList;
      console.log(file.raw.type);

        
    },
    //删除文件
    handleRemove (file, fileList) {
      console.log(file, fileList);
    },
    // 点击文件
    handlePreview (file) {
      console.log(file);
    },
    handleSelectMenu(item) {
              
              let data;
              if(item.$attrs.index=="ast")
              data=this.ast;
              else if(item.$attrs.index=="cfg")
              data=this.cfg;
              else if(item.$attrs.index=="ddg")
              data=this.ddg;

              console.log(item.$attrs.index,data);
              let _ts = this;
              _ts.$router.push({
                  name: `${item.$attrs.index}`,
                  params:{data:data},
              });
    },
    submit(){
      //console.log(this.value);
      let data={"data":this.textarea};

      //,this.getCFG(data),this.getDDG(data)
            axios.all([this.getAST(data)])
              .then(axios.spread((acct, perms) => {
                this.$notify({
                      title: '全部处理完成',
                      message: '请下拉在结果部分下载文件',
                      type: 'success',
                      duration: 0
                      });
              }));
    },

    down1(){
        const str = JSON.stringify(this.ast, null, 2);
        const url = URL.createObjectURL(new Blob(str.split('')));
        download(url, 'ast.json');
        //download(url, 'cfg.json');
    },

    down2(){
        const str = JSON.stringify(this.cfg, null, 2);
        const url = URL.createObjectURL(new Blob(str.split('')));
        download(url, 'cfg.json');
        //download(url, 'cfg.json');
    },

    down3(){
        const str = JSON.stringify(this.ddg, null, 2);
        const url = URL.createObjectURL(new Blob(str.split('')));
        download(url, 'ddg.json');
        //download(url, 'cfg.json');
    },

    down4(){
        const str = JSON.stringify(this.ast, null, 2);
        const url = URL.createObjectURL(new Blob(str.split('')));
        download(url, 'ast.json');
        download(url, 'cfg.json');
        download(url, 'ddg.json');
    },
    }
  }

</script>

<style>
* {
  box-sizing: border-box;
}

.box {
  float: left;
  width: 50%;
  padding: 40px;
}

.clearfix::after {
  content: "";
  clear: both;
  display: table;
}


</style>