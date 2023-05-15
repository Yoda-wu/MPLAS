<template>
    <div>
        

        <div class="header">
          <img src="../logo/logo.png">
        <h1>多语言代码分析系统</h1>
        </div>


        <div class="row">
        <div class="leftcolumn">
              <div class="box" ref="box">
                <div class="left">

                  <!--左侧div内容-->
                  <div class="card_l">
                    <h2>图形化结果：</h2>
                    <el-tabs v-model="activeName" type="card" @tab-click="handleSelectMenu">
                        <el-tab-pane label="AST" name="ast" index="ast"></el-tab-pane>
                        <el-tab-pane label="CFG" name="cfg" index="cfg"></el-tab-pane>
                        <el-tab-pane label="DDG" name="ddg" index="ddg"></el-tab-pane>
                        <router-view  v-if="isRresh"></router-view>
                    </el-tabs>
                  </div>

                </div>

                <div class="resize" title="收缩侧边栏">
                  ⋮
                </div>

                <div class="mid">

                  <!--右侧div内容-->
                  <div class="card_r">

                    <div class="r_top">
                      <h2>输入代码</h2>
                        <el-select v-model="value1" placeholder="请选择代码类型：">
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
                            :autosize="{ minRows: 13, maxRows: 26}"
                            v-model="textarea">
                        </el-input>

                        <div class="button">
                            <el-button type="primary" round @click="submit" style="float:right">提交</el-button>
                        </div>
                    </div>
                  
                  <!-- <div class="row">
                  <div class="card_rl">
                    <div class="card1">
                            <h2>文件上传
                            <el-tooltip placement="top"  effect="light">
                              <div slot="content">{"code":"source code1","text":"file_name1"}
                                <br/>{"code":"source code2","text":"file_name2"}
                                <br/>......
                              </div>
                              <el-button size="small" style="float: right;">文件格式</el-button>
                            </el-tooltip>
                          </h2>
                    </div>

                    <div class="card2">
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
                                :before-upload="beforeUpload"
                                :on-change="handleChange"	
                                :on-preview="handlePreview"	
                                :on-remove="handleRemove"	
                                multiple>
                                <el-button size="small" type="primary">点击上传</el-button>
                                <div slot="tip" class="el-upload__tip">只允许上传.json/.jsonl文件，且不超过12M</div>
                            </el-upload>
                            <p></p>
                      </div>
                  </div>
                    
                  <div class="card_rr">

                    <div class="card1">
                        <h2>结果下载</h2>
                    </div>

                    <div class="card2">
                      <a href="#lay" @click="down1">ast.json</a>
                      <p></p>
                      <a href="#lay" @click="down2">cfg.json</a>
                      <p></p>
                      <a href="#lay" @click="down3">ddg.json</a>
                      <p></p>
                      <el-button size="small" type="primary" @click="down4">全部下载</el-button>
                    </div>

                  </div>
                </div> -->

                </div>
                </div>
            </div>

        </div>

        <div class="rightcolumn"> 

          <div class="card1">
                <h2>文件上传</h2>
                <el-tooltip placement="top"  effect="light">
                  <div slot="content">{"code":"source code1","text":"file_name1"}
                    <br/>{"code":"source code2","text":"file_name2"}
                    <br/>......
                  </div>
                  <el-button size="small">文件格式</el-button>
                </el-tooltip>
          </div>
            <div class="card2">
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
                        :before-upload="beforeUpload"
                        :on-change="handleChange"	
                        :on-preview="handlePreview"	
                        :on-remove="handleRemove"	
                        multiple>
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只允许上传.json/.jsonl文件，且不超过12M</div>
                    </el-upload>
                    <p></p>
            </div>
          
            
            <div class="card1">
                <h2>结果下载</h2>
            </div>
            <div class="card2">
               
                <a href="#lay" @click="down1">ast.json</a>
                <p></p>
                <a href="#lay" @click="down2">cfg.json</a>
                <p></p>
                <a href="#lay" @click="down3">ddg.json</a>
                <p></p>
                <el-button size="small" type="primary" @click="down4">全部下载</el-button>
                
            </div>
            
          </div>
        </div>
    </div>

</template>

<script>
  import axios from "axios";
import { h } from "vue";




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
          value1: '',
          value2:'',
          fileList:[],
          fileStore:[],
          ast:{},
          cfg:{},
          ddg:{},
          textarea:'',
          activeName:'',
          isRresh: true,
      }
  },
  provide() {
    return {
      refresh: this.refresh,
      }
    },
  mounted(){
    this.dragControllerDiv();

    this.activeName=sessionStorage.getItem("activeName");

    if(sessionStorage.getItem("value1")!=null){
      this.value1=sessionStorage.getItem("value1");
    }
    
    if(sessionStorage.getItem("value2")!=null){
      this.value2=sessionStorage.getItem("value2");
    }

    var textJson=sessionStorage.getItem("text");
    var textEntity = JSON.parse(textJson);
    if(textEntity!=null){
      this.textarea=textEntity.text;
    }

    var fileJsonStr = sessionStorage.getItem("file");
    var fileEntity = JSON.parse(fileJsonStr);
    if(fileEntity!=null){
      this.fileStore=[fileEntity.file];
      this.fileList=[fileEntity.file];
    }
    
    var astJson=sessionStorage.getItem("ast");
    var astEntity = JSON.parse(astJson);
    if(astEntity!=null){
      this.ast=astEntity.ast;
    }
    
    var cfgJson=sessionStorage.getItem("cfg");
    var cfgEntity = JSON.parse(cfgJson);
    if(cfgEntity!=null){
      this.cfg=cfgEntity.cfg;
    }

    var ddgJson=sessionStorage.getItem("ddg");
    var ddgEntity = JSON.parse(ddgJson);
    if(ddgEntity!=null){
      this.ddg=ddgEntity.ddg;
    }
    
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
                  //console.log(this.ast);

                if(this.ast.data==null){
                  this.$notify({
                          title: 'AST警告',
                          message: 'AST获取到的分析结果为空，请查看是否正确输入',
                          type: 'warning',
                          duration: 0
                        });
                }
                else{
                  this.$notify({
                          title: 'AST处理完成',
                          message: '请在结果部分下载文件',
                          type: 'success',
                          duration: 0
                          });
                }
                    
              }
            }).catch(error=>{
                console.log(error);
                this.$notify.error({
                title: 'AST错误',
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
              //console.log(this.cfg);

            if(this.cfg.data==null){
              this.$notify({
                      title: 'CFG警告',
                      message: 'CFG获取到的分析结果为空，请查看是否正确输入',
                      type: 'warning',
                      duration: 0
                    });
            }
            else{
              this.$notify({
                      title: 'CFG处理完成',
                      message: '请在结果部分下载文件',
                      type: 'success',
                      duration: 0
                      });
            }
                
          }
        }).catch(error=>{
            console.log(error);
            this.$notify.error({
            title: 'CFG错误',
            message: 'CFG获取error',
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
              //console.log(this.ddg);

            if(this.ddg.data==null){
              this.$notify({
                      title: 'DDG警告',
                      message: 'DDG获取到的分析结果为空，请查看是否正确输入',
                      type: 'warning',
                      duration: 0
                    });
            }
            else{
              this.$notify({
                      title: 'DDG处理完成',
                      message: '请在结果部分下载文件',
                      type: 'success',
                      duration: 0
                      });
            }
                
          }
        }).catch(error=>{
            console.log(error);
            this.$notify.error({
            title: 'DDG错误',
            message: 'DDG获取error',
            duration: 0
            });
        }); 

    },
    beforeUpload(file) {

      const isLt12M = file.size / 1024 / 1024 < 12;

      if(this.value2==''){
      this.$message.error('请选择语言类型');
    }
    else{

      sessionStorage.setItem("value2",this.value2);

        if (!isLt12M) {
          this.$message.error('上传文件大小不能超过 12MB!');
        }
      }
        return isLt12M;
    },
      async upload(){

        if(this.fileList!=this.fileStore){
          let value=this.value2;
          var axios = require('axios');
          var FormData = require('form-data');
          let formData=new FormData();

          formData.append("data",this.fileList[0].raw);

          axios.all([this.getAST(formData,value),this.getCFG(formData,value),this.getDDG(formData,value)])
            .then(axios.spread((acct, perms) => {
              this.$message({
                  message: '上传成功，请等待处理完成',
                  type: 'success'
        });
            }));

        }
        
      },
  //点击上传文件触发的额外事件,清空fileList
  delFile () {
    this.fileList = [];
  },
  handleChange (file,fileList) {
    if(file.size>12*1024*1024){
      this.fileList=this.fileStore;
    }
    else{
      this.fileList =[file];
    var data={
      "file":file
     };
    sessionStorage.setItem("file",JSON.stringify(data));
    }

    this.handleRemove(fileList[0],fileList);
    
  },
  //删除文件
  handleRemove (file, fileList) {
    fileList=[file];
  },
  // 点击文件
  handlePreview (file) {
    console.log(file);
  },
  handleSelectMenu(item) {

    sessionStorage.setItem("activeName",item.$attrs.index);
    console.log(this.activeName);

            let data;
            if(item.$attrs.index=="ast")
            data=this.ast.data;
            else if(item.$attrs.index=="cfg")
            data=this.cfg.data;
            else if(item.$attrs.index=="ddg")
            data=this.ddg.data;

            console.log(data);
            if(data!=null){
              if(data[0].file!="textarea"){
                data={};
              }
            }

            console.log(data);

            let _ts = this;
              _ts.$router.push({
                  name: `${item.$attrs.index}`,
                  params:{data:data},
              }, () => {});      

  },
  submit(){
    //console.log(this.value);
    var textSto={
                    "text":this.textarea
                  }
    sessionStorage.setItem("text",JSON.stringify(textSto));

    
    if(this.value1==''){
      this.$message.error('请选择语言类型');
    }
    else{
      let value=this.value1;

      sessionStorage.setItem("value1",value);

      var axios = require('axios');
      let data={"code":this.textarea,
                "text":"textarea"};

      let dataJson=JSON.stringify(data);
      let ne=new Blob([dataJson]);
      console.log(ne);

      var FormData = require('form-data');
      let formData=new FormData();

      formData.append("data",ne);

      //,this.getCFG(data),this.getDDG(data)
        axios.all([this.getAST(formData,value),this.getCFG(formData,value),this.getDDG(formData,value)])
          .then(axios.spread((acct, perms) => {
            this.$message({
            message: '提交成功，请等待处理完成',
            type: 'success'
          });
          }));
    }
    
  },
  
  down1(){
    if(this.ast.data==null){
      alert('ast分析结果为空，请先提交代码或上传文件');
    }
    else{
      const str = JSON.stringify(this.ast, null, 2);
      const url = URL.createObjectURL(new Blob(str.split('')));
      download(url, 'ast.json');
    }
      
  },

  down2(){if(this.cfg.data==null){
      alert('cfg分析结果为空，请先提交代码或上传文件');
    }
    else{
      const str = JSON.stringify(this.cfg, null, 2);
      const url = URL.createObjectURL(new Blob(str.split('')));
      download(url, 'cfg.json');
    }
  },

  down3(){
    if(this.ddg.data==null){
      alert('ddg分析结果为空，请先提交代码或上传文件');
    }
    else{
      const str = JSON.stringify(this.ddg, null, 2);
      const url = URL.createObjectURL(new Blob(str.split('')));
      download(url, 'ddg.json');
    }
  },

  down4(){
      this.down1();
      this.down2();
      this.down3();
  },

  dragControllerDiv () {
                var resize = document.getElementsByClassName('resize');
                var left = document.getElementsByClassName('left');
                var mid = document.getElementsByClassName('mid');
                var box = document.getElementsByClassName('box');
                for (let i = 0; i < resize.length; i++) {
                    // 鼠标按下事件
                    resize[i].onmousedown = function (e) {
                        //颜色改变提醒
                        resize[i].style.background = '#818181';
                        var startX = e.clientX;
                        resize[i].left = resize[i].offsetLeft;
                        // 鼠标拖动事件
                        document.onmousemove = function (e) {
                            var endX = e.clientX;
                            var moveLen = resize[i].left + (endX - startX); // （endx-startx）=移动的距离。resize[i].left+移动的距离=左边区域最后的宽度
                            var maxT = box[i].clientWidth - resize[i].offsetWidth; // 容器宽度 - 左边区域的宽度 = 右边区域的宽度

                            if (moveLen < 270) moveLen = 270; // 左边区域的最小宽度为50px
                            if (moveLen > maxT - 150) moveLen = maxT - 150; //右边区域最小宽度为150px

                            resize[i].style.left = moveLen; // 设置左侧区域的宽度

                            for (let j = 0; j < left.length; j++) {
                                left[j].style.width = moveLen + 'px';
                                mid[j].style.width = (box[i].clientWidth - moveLen - 20) + 'px';
                            }
                        };
                        // 鼠标松开事件
                        document.onmouseup = function (evt) {
                            //颜色恢复
                            resize[i].style.background = '#d6d6d6';
                            document.onmousemove = null;
                            document.onmouseup = null;
                            resize[i].releaseCapture && resize[i].releaseCapture(); //当你不在需要继续获得鼠标消息就要应该调用ReleaseCapture()释放掉
                        };
                        resize[i].setCapture && resize[i].setCapture(); //该函数在属于当前线程的指定窗口里设置鼠标捕获
                        return false;
                    };
                }
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
  background: rgb(252, 253, 248);
}

/* 页眉/Blog 标题 */
.header {
  padding: 5px;
  text-align: left;
  background: rgb(252, 253, 248);
}

.header h1 {
  font-size: 40px;
  margin: 20px;
}

img {
  width: 80px;
  float: left;
}

/* 创建两个不相等的彼此并排的浮动列 */
/* 左列 */
.leftcolumn {   
  float: left;
  width: 87%;
  background-color: rgb(252, 253, 248);
  padding-left: 0px;
}


/* 右列 */
.rightcolumn {
  float: left;
  width: 13%;
  background-color: rgb(252, 253, 248);
  padding-left: 10px;
  padding-right: 0px;
}


.button {
  background-color: #ffffff;
  width: 100%;
  padding: 10px;
  margin-bottom: 25px;
}

/* 为文章添加卡片效果 */
.card_l {
  height: 750px;
  background-color: white;
  padding: 15px;
  border: 1px solid black;
  border-radius: 5px;
}

.card_r {
  height: 750px;
  background-color: white;
  padding: 10px;
  border: 1px solid black;
  border-radius: 5px;
}

.r_top{
  background-color: white;
  padding: 5px;
}

.card_rl {
  width:49%;
  float: left;
  height: auto;
  background-color: white;
  margin-top: 10px;
  margin-bottom: 10px;
  /* border: 5px;
  border-style: solid;
  border-color:#f1f1f1; */
}

.card_rr {
  width:49%;
  float: right;
  height: auto;
  background-color: white;
  margin-top: 10px;
  margin-bottom: 10px;
  /* border: 5px;
  border-style: solid;
  border-color:#f1f1f1; */
}

.card1 {
  background-color: #f1f1f1;
  padding:5px;
  margin-top: 20px;
  text-align: center;
  border: 5px;
  border-style: solid;
  border-color:#f1f1f1;
}

.card2 {
  background-color: #ffffff;
  padding:15px;
  margin-top: 0px;
  border: 5px;
  border-style: solid;
  border-color:#f1f1f1;
}

.card1 h2{
    margin: 5px;
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





  /* 拖拽相关样式 */
    /*包围div样式*/
    .box {
        width: 100%;
        height: 100%;
        margin: 1% 0px;
        overflow: hidden;
        
    }
    /*左侧div样式*/
    .left {
        width: calc(30% - 10px);  /*左侧初始化宽度*/   
        height: 100%;
        background: #FFFFFF;
        float: left;
    }
    /*拖拽区div样式*/
    .resize {
        cursor: col-resize;
        float: left;
        position: relative;
        top: 50%;
        background-color: #d6d6d6;
        border-radius: 5px;
        margin-top: 200px;
        width: 10px;
        height: 50px;
        background-size: cover;
        background-position: center;
        /*z-index: 99999;*/
        font-size: 32px;
        color: white;
    }
    /*拖拽区鼠标悬停样式*/
    .resize:hover {
        color: #444444;
    }
    /*右侧div'样式*/
    .mid {
        float: left;
        width: 70%;   /*右侧初始化宽度*/
        height: 100%;
        background: rgb(252, 253, 248);
        box-shadow: -1px 0px 5px 3px rgba(0, 0, 0, 0.11);
    }


</style>