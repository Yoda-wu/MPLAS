<template>
    <div class="content">
        <div v-html="mermaidCode" class="content-html"></div>
  </div>
  </template>
  
  <script>
  import mermaid from "mermaid";
  import {mermaidCode} from "../js/transForCFG";
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
        let cfg=this.$route.params.data;

        

        if(cfg!=undefined)
        {
            var data2={
                "cfgFir":cfg[0],
            };
          sessionStorage.setItem("title",cfg[0].file);
          sessionStorage.setItem("data2",JSON.stringify(data2));
        }
        var cfgJsonStr = sessionStorage.getItem("data2");
        
        var cfg1 = JSON.parse(cfgJsonStr);

        console.log(cfg1);

        let result=mermaidCode(cfg1.cfgFir);
        
        
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

<style scoped>
.file_title{
  font-size: 20px;
}

.content{
  width: auto;
  height: 600px;
  overflow: auto;
  white-space: nowrap;
  }


.mermaid .edgeLabel{
  background-color:#FFFFFF;
  text-align:left;
  opacity:1 !important;
}
.mermaid .label {
  padding:1rem .25rem !important;
}

.content-html>>>p{
    color: aqua;
  }

  .content-html>>>.mermaid .label{
    padding:1rem .25rem !important;
  }

</style>
  