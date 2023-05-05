
function transform(ddg){
    let nodes=ddg.nodes;
    let temp="";
    let source,target;
    let start='',end='',label='';
    for (let index = 0; index < ddg.edges.length; index++) {
        source=ddg.edges[index].source;
        target=ddg.edges[index].target;
        start='',end='',label='';
        if(nodes[source]!=null){
          if(nodes[source].defs!=null){
            start="[\"id:"+nodes[source].id+"<br>line:"+nodes[source].line+"<br>defs:["+nodes[source].defs
                  +"]<br>uses:["+nodes[source].uses+"]<br>label:"+nodes[source].label+"\"]";
          }
          else{
            start="[\"id:"+nodes[source].id+"<br>line:"+nodes[source].line+"<br>defs:["+nodes[source].defs
                  +"]<br>uses:["+nodes[source].uses+"]<br>label:"+nodes[source].label+"\"]";
          }
        }

        if(nodes[target]!=null){
        end="[\"id:"+nodes[source].id+"<br>line:"+nodes[source].line+"<br>defs:["+nodes[source].defs
        +"]<br>uses:["+nodes[source].uses+"]<br>label:"+nodes[source].label+"\"]";
        }

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
  let temp="flowchart TD;"+tran_ddg;
  //let temp="flowchart TD;1-->2("+"\"This is the (text) in the box"+"\")";
  let n=`<div class="mermaid" id="mermaidChart">`+temp+`</div>`;
  return n;
}


export{
  transform,
  mermaidCode,
}