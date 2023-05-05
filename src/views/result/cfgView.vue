<template>
    <div v-html="mermaidCode"></div>
  </template>
  
  <script>
  import mermaid from "mermaid";
  import {mermaidCode} from "../js/transForCFG";
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
        let cfg=this.$route.params.data;

        console.log(cfg);
        if(cfg!=undefined)
        {
            var data={
                "cfgFir":cfg[0],
            };
            
          sessionStorage.setItem("data",JSON.stringify(data));
        }
        var cfgJsonStr = sessionStorage.getItem("data");
        
        var cfg1 = JSON.parse(cfgJsonStr);

        console.log(cfg1);

        let result=mermaidCode(cfg1.cfgFir);
        
        
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
  