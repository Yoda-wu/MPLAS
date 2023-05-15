<template>
  
  <div class="content">
    <div v-html="mermaidCode" class="content-html"></div>

  </div>
     
    

  </template>
  
  <script>
  import mermaid from "mermaid";
  import {mermaidCode} from "../js/transForAST";


  let config = {
            startOnLoad: true,
            //theme: 'neutral',
            flowchart: {
                useMaxWidth: false,
                htmlLabels: true,
                curve: 'Basis'
            },
            securityLevel: 'loose',
        }

  mermaid.initialize(config);



  export default {
    data() {
      return {

        title:"",
      }
    },
    computed: {
      mermaidCode() {
        let ast=this.$route.params.data;

        
        if(ast!=undefined)
        {
            var data={
                "astFir":ast[0],
            };
            
          sessionStorage.setItem("title",ast[0].file);
          sessionStorage.setItem("data",JSON.stringify(data));
        }
        var astJsonStr = sessionStorage.getItem("data");
        
        var ast1 = JSON.parse(astJsonStr);

        let result=mermaidCode(ast1.astFir);
        
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

.mermaid .label {
  width: 10px;
}

svg[id^="mermaid-"] {
            min-width: 900px;
            min-height: 600px;
        }

.content-html>>>.mermaid {
    width: 900px;
    margin: auto;
}

.content{
  width: auto;
  height: 600px;
  overflow: auto;
  white-space: nowrap;
  }

  .content-html>>>p{
    color: aqua;
  }
   
  .content-html>>>svg[id^="mermaid-"] {
           /*  max-width: 5000px; */
            font-size: 8px;
        } 

</style>

