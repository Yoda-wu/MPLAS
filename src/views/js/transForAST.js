
function transform(ast){
    let nodes=ast.nodes;
    let temp="";
    let source,target;
    let start='',end='';
    for (let index = 0; index < ast.edges.length; index++) {
        source=ast.edges[index].source;
        target=ast.edges[index].target;
        start='',end='';

        start="[\"id:"+nodes[source].id+"<br>line:"+nodes[source].line+"<br>label:"+nodes[source].label
                +"<br>normalized:"+nodes[source].normalized+"<br>type:"+nodes[source].type+"\"]";


        end="[\"id:"+nodes[source].id+"<br>line:"+nodes[source].line+"<br>label:"+nodes[source].label
                +"<br>normalized:"+nodes[source].normalized+"<br>type:"+nodes[source].type+"\"]";


      temp=temp+source+start+"-->"+target+end+";";
      
    }
    //console.log(temp);
    return temp;
}

function mermaidCode(ast) {


  let tran_ast=transform(ast);
  let temp="flowchart TD;"+tran_ast;
  //let temp="flowchart TD;1-->2("+"\"This is the (text) in the box"+"\")";
  let n=`<div class="mermaid" id="mermaidChart">`+temp+`</div>`;

  return n;
}


export{
  mermaidCode,
}