
function transform(cfg){
    let nodes=cfg.nodes;
    let temp="";
    let source,target;
    let start='',end='',label='';
    for (let index = 0; index < cfg.edges.length; index++) {
        source=cfg.edges[index].source;
        target=cfg.edges[index].target;
        start='',end='',label='';

        start="[\"id:"+nodes[source].id+"<br>line:"+nodes[source].line+"<br>label:"+nodes[source].label+"\"]";

        end="[\"id:"+nodes[target].id+"<br>line:"+nodes[target].line+"<br>label:"+nodes[target].label+"\"]";
        
        if(cfg.edges[index].label!="")
          label="|\"label:"+cfg.edges[index].label+"\"|";
        temp=temp+source+start+"-->"+label+target+end+";";
        
        //nodes[source]=null;
        //nodes[target]=null;
    }
    //console.log(temp);
    return temp;
}

function mermaidCode(cfg) {
  
  let tran_cfg=transform(cfg);
  let temp="flowchart TD;"+tran_cfg;
  //let temp="flowchart TD;1-->2("+"\"This is the (text) in the box"+"\")";
  let n=`<div class="mermaid" id="mermaidChart">`+temp+`</div>`;
  return n;
}


export{
  mermaidCode,
}