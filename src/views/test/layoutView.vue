<template>
    <div>

        <div class="header">
        <h1>多语言代码分析系统</h1>
        </div>


        <div class="row">
        <div class="leftcolumn">
            <div class="card">
                <h2>输入代码</h2>
                <p>请选择语言后在下方输入框输入代码</p>
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
                    :autosize="{ minRows: 13, maxRows: 13}"
                    v-model="textarea">
                </el-input>

                <div class="button">
                    <el-button type="primary" round @click="submit" style="float:right">提交</el-button>
                </div>

                <p>在处理结果中可以下载结果文件，以及查看图形化结果</p>
            </div>

        </div>
        <div class="rightcolumn">
            <div class="card">
                <h2>关于批量处理</h2>
                <p>如果有批量处理函数的需要，可以在下面上传文件 </p>
                <p>
                  请注意上传文件格式如下：
                  {"code":source code,
                    "text":name}
                </p>
            </div>
            <div class="card">
                <h2>文件上传</h2>
                <el-select v-model="value2" placeholder="请选择">
                    <el-option
                    v-for="item in options"
                    :key="item.label"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
                <p></p>
                <el-upload
                        class="upload-demo"
                        action=""
                        accept=".json,.jsonl"
                        :file-list="fileList"	
                        :http-request="upload"
                        :on-change="handleChange"	
                        :on-preview="handlePreview"	
                        :on-remove="handleRemove"	
                        multiple>
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只允许上传.json/.jsonl文件，且不超过16M</div>
                    </el-upload>
                    <p>下拉在处理结果中下载所需文件</p>
            </div>
        </div>
        </div>

        <div class="footer">
        <h2>关于处理结果</h2>
        </div>

        <div class="result">
            <div class="card">
                <h3>结果下载：</h3>
                <a href="#lay" @click="down1">ast.json</a>
                <p></p>
                <a href="#lay" @click="down2">cfg.json</a>
                <p></p>
                <a href="#lay" @click="down3">ddg.json</a>
                <!-- <el-button type="success" @click="down1">下载ast.json</el-button>
                <el-button type="success" @click="down2">下载cfg.json</el-button>
                <el-button type="success" @click="down3">下载ddg.json</el-button>
                <el-button type="success" @click="down4">全部下载</el-button> -->
            </div>

            <div class="card">
                <h3>图形化结果：</h3>
                <el-tabs v-model="activeName" type="card" @tab-click="handleSelectMenu">
                    <el-tab-pane label="AST" name="first" index="ast"></el-tab-pane>
                    <el-tab-pane label="CFG" name="second" index="cfg"></el-tab-pane>
                    <el-tab-pane label="DDG" name="third" index="ddg"></el-tab-pane>
                    <router-view  v-if="isRresh"></router-view>
                </el-tabs>
            </div>
        </div>

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
              label: 'Java',
              lang:'java'
              }, {
              value: '2',
              label: 'C++',
              lang:'cpp'
              }, {
              value: '3',
              label: 'Rudy',
              lang:'ruby'
              }, {
              value: '4',
              label: 'JavaScript',
              lang:'js'
              }],
          value: '1',
          value2:'2',
          fileList:[],
          ast:{},
          cfg:{},
          ddg:{},
          textarea:'',

          isRresh: true,
      }
  },
  provide() {
    return {
      refresh: this.refresh,
      }
    },
  mounted(){
    var fileJsonStr = sessionStorage.getItem("file");
    var fileEntity = JSON.parse(fileJsonStr);
    this.fileList=[fileEntity.file];
    var astJson=sessionStorage.getItem("ast");
    var astEntity = JSON.parse(astJson);
    this.ast=astEntity.ast;
    var cfgJson=sessionStorage.getItem("cfg");
    var cfgEntity = JSON.parse(cfgJson);
    this.cfg=cfgEntity.cfg;
    var ddgJson=sessionStorage.getItem("ddg");
    var ddgEntity = JSON.parse(ddgJson);
    this.ddg=ddgEntity.ddg;
    var textJson=sessionStorage.getItem("text");
    var textEntity = JSON.parse(textJson);
    this.textarea=textEntity.text;
  },
  methods:{
    getAST(data,value){

        let lang=this.options[value-1].lang;

        var axios = require('axios');
          
          var config = {
            method: 'post',
            url: '/api/ast?lang='+lang,
            headers: { 
                'Accept': '*/*', 
            },
            data : data
          };

           axios(config).then((response)=> {
              if (response.data) {
                  this.ast=response.data;
                  var astSto={
                    "ast":this.ast
                  }
                  sessionStorage.setItem("ast",JSON.stringify(astSto));
                  console.log(this.ast);

                if(this.ast.data==null){
                  this.$notify({
                          title: '警告',
                          message: 'AST获取到的分析结果为空，请查看是否正确输入',
                          type: 'warning',
                          duration: 0
                        });
                }
                else{
                  this.$notify({
                          title: 'AST处理完成',
                          message: '请下拉在结果部分下载文件',
                          type: 'success',
                          duration: 0
                          });
                }
                    
              }
            }).catch(error=>{
                console.log(error);
                this.$notify.error({
                title: '错误',
                message: 'AST获取error',
                duration: 0
                });
            }); 

    },
    getCFG(data,value){

      let lang=this.options[value-1].lang;

      var axios = require('axios');
  
      var config = {
        method: 'post',
        url: '/api/cfg?lang='+lang,
        headers: { 
            'Accept': '*/*', 
        },
        data : data
      };

      axios(config).then((response)=> {
          if (response.data) {
              this.cfg=response.data;
              var cfgSto={
                    "cfg":this.cfg
                  }
              sessionStorage.setItem("cfg",JSON.stringify(cfgSto));
              console.log(this.cfg);

            if(this.cfg.data==null){
              this.$notify({
                      title: '警告',
                      message: 'CFG获取到的分析结果为空，请查看是否正确输入',
                      type: 'warning',
                      duration: 0
                    });
            }
            else{
              this.$notify({
                      title: 'CFG处理完成',
                      message: '请下拉在结果部分下载文件',
                      type: 'success',
                      duration: 0
                      });
            }
                
          }
        }).catch(error=>{
            console.log(error);
            this.$notify.error({
            title: '错误',
            message: 'CFG获取发生错误',
            duration: 0
            });
        }); 
 
    },
    getDDG(data,value){

      let lang=this.options[value-1].lang;

      var axios = require('axios');
  
      var config = {
        method: 'post',
        url: '/api/ddg?lang='+lang,
        headers: { 
            'Accept': '*/*', 
        },
        data : data
      };

      axios(config).then((response)=> {
          if (response.data) {
              this.ddg=response.data;
              var ddgSto={
                    "ddg":this.ddg
                  }
                  sessionStorage.setItem("ddg",JSON.stringify(ddgSto));
              console.log(this.ddg);

            if(this.ddg.data==null){
              this.$notify({
                      title: '警告',
                      message: 'DDG获取到的分析结果为空，请查看是否正确输入',
                      type: 'warning',
                      duration: 0
                    });
            }
            else{
              this.$notify({
                      title: 'DDG处理完成',
                      message: '请下拉在结果部分下载文件',
                      type: 'success',
                      duration: 0
                      });
            }
                
          }
        }).catch(error=>{
            console.log(error);
            this.$notify.error({
            title: '错误',
            message: 'DDG获取error',
            duration: 0
            });
        }); 

    },
      async upload(){
        let value=this.value2;
          var axios = require('axios');
          var FormData = require('form-data');
          let formData=new FormData();

          formData.append("data",this.fileList[0].raw);
/* ,this.getCFG(formData),this.getDDG(formData) */
          axios.all([this.getAST(formData,value),this.getCFG(formData,value),this.getDDG(formData,value)])
            .then(axios.spread((acct, perms) => {
              this.$message({
                  message: '上传成功，请等待处理完成',
                  type: 'success'
        });
            }));

      },
  //点击上传文件触发的额外事件,清空fileList
  delFile () {
    this.fileList = [];
  },
  handleChange (file, fileList) {
    this.fileList =[file];
    var data={
      "file":file
     };
    sessionStorage.setItem("file",JSON.stringify(data));

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
            data=this.ast.data;
            else if(item.$attrs.index=="cfg")
            data=this.cfg.data;
            else if(item.$attrs.index=="ddg")
            data=this.ddg.data;

            console.log(item.$attrs.index,data);
            let _ts = this;
            _ts.$router.push({
                name: `${item.$attrs.index}`,
                params:{data:data},
            }, () => {});

            this.activeName="first";
  },
  submit(){
    //console.log(this.value);
    var textSto={
                    "text":this.textarea
                  }
    sessionStorage.setItem("text",JSON.stringify(textSto));

    let value=this.value;

    var axios = require('axios');
    let data={"code":this.textarea,
              "text":"textarea.java"};

    let dataJson=JSON.stringify(data);
    let ne=new Blob([dataJson]);
    console.log(ne);

    var FormData = require('form-data');
    let formData=new FormData();

    formData.append("data",ne);

    //,this.getCFG(data),this.getDDG(data)
      axios.all([this.getAST(formData,value)])
        .then(axios.spread((acct, perms) => {
          this.$message({
          message: '提交成功，请等待处理完成',
          type: 'success'
        });
        }));
  },

  down1(){
      const str = JSON.stringify(this.ast, null, 2);
      const url = URL.createObjectURL(new Blob(str.split('')));
      download(url, 'ast.json');
  },

  down2(){
      const str = JSON.stringify(this.cfg, null, 2);
      const url = URL.createObjectURL(new Blob(str.split('')));
      download(url, 'cfg.json');
  },

  down3(){
      const str = JSON.stringify(this.ddg, null, 2);
      const url = URL.createObjectURL(new Blob(str.split('')));
      download(url, 'ddg.json');
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
/* rgb(236, 245, 255) */
body {
  font-family: Arial;
  padding: 10px;
  background: #f1f1f1;
}

/* 页眉/Blog 标题 */
.header {
  padding: 5px;
  text-align: left;
  background: rgb(255, 255, 255);
}

.header h1 {
  font-size: 40px;
  margin: 20px;
}


/* 设置上导航栏的样式 */
.topnav {
  overflow: hidden;
  background-color: #333;
}

/* 设置 topnav 链接的样式 */
.topnav a {
  float: left;
  display: block;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

/* 改变鼠标悬停时的颜色 */
.topnav a:hover {
  background-color: #ddd;
  color: black;
}

/* 创建两个不相等的彼此并排的浮动列 */
/* 左列 */
.leftcolumn {   
  float: left;
  width: 75%;
}

/* 右列 */
.rightcolumn {
  float: left;
  width: 25%;
  background-color: #f1f1f1;
  padding-left: 20px;
}

/* 伪图像 */
.fakeimg {
  background-color: #aaa;
  width: 100%;
  padding: 20px;
}

.button {
  background-color: #ffffff;
  width: 100%;
  padding: 10px;
}

/* 为文章添加卡片效果 */
.card {
  background-color: white;
  padding: 20px;
  margin-top: 20px;
}

/* 清除列之后的浮动 */
.row:after {
  content: "";
  display: table;
  clear: both;
}

/* 页脚 */
.footer {
  padding: 10px;
  text-align: center;
  background: #ddd;
  margin-top: 20px;
}

.footer h2{
    margin: 5px;
}

/* 响应式布局 - 当屏幕的宽度小于 800 像素时，使两列堆叠而不是并排 */
@media screen and (max-width: 800px) {
  .leftcolumn, .rightcolumn {   
    width: 100%;
    padding: 0;
  }
}

/* 响应式布局 - 当屏幕的宽度小于 400 像素时，使导航链接堆叠而不是并排 */
@media screen and (max-width: 400px) {
  .topnav a {
    float: none;
    width: 100%;
  }
}
</style>