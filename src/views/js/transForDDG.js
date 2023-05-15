
function transform(ddg){
    let nodes=ddg.nodes;
    let temp="";
    let source,target;
    let start='',end='',label='',defs='',uses='';
    for (let index = 0; index < ddg.edges.length; index++) {
        source=ddg.edges[index].source;
        target=ddg.edges[index].target;
        start='',end='',label='',defs='',uses='';

        if(nodes[source].defs!=null){
          defs="<br>defs:["+nodes[source].defs+"]";
        }
        if(nodes[source].uses!=null){
          uses="<br>uses:["+nodes[source].uses+"]";
        }

        start="[\"id:"+nodes[source].id+"<br>line:"+nodes[source].line+defs+uses+"<br>label:"+nodes[source].label+"\"]";
        defs='',uses='';

        if(nodes[target].defs!=null){
          defs="<br>defs:["+nodes[target].defs+"]";
        }
        if(nodes[target].uses!=null){
          uses="<br>uses:["+nodes[target].uses+"]";
        }

        end="[\"id:"+nodes[target].id+"<br>line:"+nodes[target].line+defs+uses+"<br>label:"+nodes[target].label+"\"]";

        if(ddg.edges[index].label!=""){
          label="|\"label:"+ddg.edges[index].label+"<br>type:"+ddg.edges[index].type+"\"|";
        }
        else{
          label="|\"type:"+ddg.edges[index].type+"\"|";
        }
        temp=temp+source+start+"-->"+label+target+end+";";
        //nodes[source]=null;
        //nodes[target]=null;
    }
    //console.log(temp);
    return temp;
}

function mermaidCode(ddg) {
  //let cfg=this.$route.params.node;
  console.log(ddg);

  let tran_ddg=transform(ddg);
  let temp="%%{init: {'themeVariables': {'fontSize':'6px'}}}%%\nflowchart TD;"+tran_ddg;
  //let temp="flowchart TD;1-->2("+"\"This is the (text) in the box"+"\")";
  let n=`<div class="mermaid" id="mermaidChart">`+temp+`</div>`;
  return n;
}


export{
  transform,
  mermaidCode,
}