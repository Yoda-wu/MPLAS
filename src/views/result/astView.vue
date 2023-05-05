<template>
    <div v-html="mermaidCode"></div>
  </template>
  
  <script>
  import mermaid from "mermaid";
  import {mermaidCode} from "../js/transForAST";
  mermaid.initialize({
          startOnLoad: true,
          securityLevel:"loose"
      });

  export default {
    data() {
      return {
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
      if (location.href.indexOf("#reloaded")<=0) {
        location.href = location.href+"#reloaded";
        location.reload();
      }
    }, 

    created(){
      
    },

  }
  </script>
  