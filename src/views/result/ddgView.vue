<template>
    <div v-html="mermaidCode"></div>
  </template>
  
  <script>
  import mermaid from "mermaid";
  import {mermaidCode} from "../js/transForDDG";
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
        let ddg=this.$route.params.data;

        
        if(ddg!=undefined)
        {
            var data={
                "ddgFir":ddg[0],
            };
            
        
          sessionStorage.setItem("data",JSON.stringify(data));
        }
        var ddgJsonStr = sessionStorage.getItem("data");
        
        var ddg1 = JSON.parse(ddgJsonStr);

        let result=mermaidCode(ddg1.ddgFir);
        
        
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
  