<template>
    <div class="content">
        <div v-html="mermaidCode" class="content-html"></div>
  </div>
  </template>
  
  <script>
  import mermaid from "mermaid";
  import {mermaidCode} from "../js/transForDDG";
  mermaid.initialize({
          startOnLoad: true,
          securityLevel:"loose",
          flowchart:{
                    useMaxWidth:false,
                    htmlLabels: true 
                }
      });

  export default {
    data() {
      return {
        title:"",
      }
    },
    computed: {
      mermaidCode() {
        let ddg=this.$route.params.data;

        
        if(ddg!=undefined)
        {
            var data3={
                "ddgFir":ddg[0],
            };
            
          sessionStorage.setItem("title",ddg[0].file);
          sessionStorage.setItem("data3",JSON.stringify(data3));
        }
        var ddgJsonStr = sessionStorage.getItem("data3");
        
        var ddg1 = JSON.parse(ddgJsonStr);

        let result=mermaidCode(ddg1.ddgFir);
        
        
        return result;
      },
      
    },
    methods:{

    },
    mounted: function() {
      this.title = sessionStorage.getItem("title");
      if (location.href.indexOf("#reloaded")<=0) {
        location.href = location.href+"#reloaded";
        location.reload();
      }
    }, 

    created(){
      
    },

  }
  </script>

<style>
.file_title{
  font-size: 20px;
}

.content{
  width: auto;
  height: 600px;
  overflow: auto;
  white-space: nowrap;
  }

</style>
  