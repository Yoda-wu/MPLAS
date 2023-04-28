
  //this.getMaxLine(ast.nodes[0].id);


  function getCtx(){
    let cs = document.getElementById('canvas');
    let ctx = cs.getContext('2d');
    return ctx;
    }


    
  //绘制节点框函数
  function drawTextItem({ startX, startY, width, height, line,type ,label,normalized }) {
    let ctx = getCtx();
    // 重新开辟一个线路
    ctx.beginPath();
    // 画一个外边框
    ctx.rect(startX, startY, width, height);
    ctx.strokeStyle = "black";
    //ctx.fillStyle = "rgb(194, 225, 252)";
    ctx.fillStyle = "white";
    ctx.stroke();
    ctx.fill();
        
    // 写字
    ctx.fillStyle = "black";
    ctx.textBaseline = "middle";
    //console.log(startY+height/2);
    // fillText(text, x, y, maxWidth)，maxWidth最大宽度
    // x,y参数其实是文字左下角的那个点的坐标
    let { width:textWidth1 } = ctx.measureText(line);
    let { width:textWidth2 } = ctx.measureText(type);
    let { width:textWidth3 } = ctx.measureText(label);
    let { width:textWidth4 } = ctx.measureText(normalized);
    ctx.fillText(line, startX+(width-textWidth1)/2, startY+height/2-20, textWidth1);
    ctx.fillText(type, startX+(width-textWidth2)/2, startY+height/2-7, textWidth2);
    ctx.fillText(label, startX+(width-textWidth3)/2, startY+height/2+7, textWidth3);
    ctx.fillText(normalized, startX+(width-textWidth4)/2, startY+height/2+20, textWidth4);
     }

     //this.drawTextItem({startX:10,startY:10,width:70,height:50,title:"title"})
  
  //绘制连接线函数
  function drawLine(nodeX,nodeY,childX,childY){
    let ctx = getCtx();
    let nodeWidth = 80;
    let nodeHeight = 60;
    //let nodeMargin = 12;

    //改成直线连接
    ctx.beginPath();
    ctx.moveTo(nodeX+(nodeWidth/2),nodeY+nodeHeight);
    ctx.lineTo(childX+(nodeWidth/2),childY);
    ctx.stroke();
    
    // 重新开辟一个线路
    /* 
    ctx.beginPath();
    
    ctx.moveTo(nodeX+(nodeWidth/2),nodeY+nodeHeight);
    
    ctx.lineTo(nodeX+(nodeWidth/2),nodeY+nodeHeight+(nodeMargin/2));
    
    ctx.lineTo(childX+(nodeWidth/2),childY-(nodeMargin/2));
    
    ctx.lineTo(childX+(nodeWidth/2),childY);
    
    ctx.stroke() // 将起点和终点连接起来
        */
    }





  //获取到每一个节点对应于哪一行
  function getNodesLine(ast){
    let nodes_line=[];
    for (let index = 0; index < ast.nodes.length; index++) {
      nodes_line[index]=0;  //初始化每个节点都在第0行
    }
    //每一个节点对应在哪一行
    for (let index = 0; index < ast.edges.length; index++) {
      let source=ast.edges[index].source;
      let target=ast.edges[index].target;
      nodes_line[target]=nodes_line[source]+1;
    }
    //console.log(nodes_line);
    return nodes_line;
  }


  //获取到每一个节点的第一个子节点
  function getFirstNode(ast){
    let first_node=[];
    for (let index = 0; index < ast.nodes.length; index++) {
      for (let m = 0; m < ast.edges.length; m++) {
        if(ast.edges[m].source==index){
          first_node[index]=ast.edges[m].target;
          break;
        }
      }
      if(first_node[index]==null)
      first_node[index]=0;
    }
    return first_node;
  }



  //获取到一个二维数组，存储了每一行中的所有元素
  //line[0]=[0]
  //line[1]=[1]
  //line[2]=[2,3,4]
  //...
  function getLine(ast){
    //console.log(nodes_line);
    let line=[];  //用来存储每一行中的元素
    let line_index=[];  //对每一行元素个数的记录，也索引元素加入到每一行中
    let nodes_line=getNodesLine(ast);
    //遍历边的关系，得到每一行的元素
    for (let index = 0; index < nodes_line.length; index++) {
      let every_node_line=nodes_line[index];  //index对应的节点在哪一行（every_node_line）
      //当line_index以及line的每一行没有定义的时候，为他们赋零或定义
      if(line_index[every_node_line]){let t=0;}
      else {line_index[every_node_line]=0;}
      if(line[every_node_line]){let t=0;}
      else {line[every_node_line]=[]}
      //将节点对应到相应的行中
      let next_item=line_index[every_node_line];
      line[every_node_line][next_item]=index;
      line_index[every_node_line]++;
    }
    //console.log(line);
    return line;
  }


/* 

  //获取某个节点node下一层子节点的集合nodes
  function getNextNodes(node,ast){
    let nodes=[];
    let i=0;
    for (let index = 0; index < ast.edges.length; index++) {
       if(ast.edges[index].source==node)
       {
        nodes[i]=ast.edges[index].target;
        i++;
       }
    }
    return nodes;
  }


  

  //获取某个节点所有的子节点
  function getNodes(node){
    let nodes=getNextNodes(node);
    let index=0;
    for (; index < nodes.length; index++) {
      let n_temp=getNextNodes(nodes[index]);
      nodes=[...nodes,...n_temp];
      //console.log(nodes,index);
    }
    return nodes;
  } 

 */
  //计算每一个节点在树中应该占据的位置大小，通过子树所要占据的位置决定子树根节点的大小
  //从最底层开始，没有子节点就赋1，否则要加上子节点所需要的位置大小
  //比如一个节点有三个子节点，分别需要3,2,1个位置块，那么它需要六个位置块
  function getCount(ast){
    let line=getLine(ast);
    let node_count=[];
    for (let index = 0; index < ast.nodes.length; index++) {
      node_count[index]=0;
    }
    for (let index = line.length-1; index >=0; index--) {
      let temp_line=line[index];
      for (let m=0 ; m < temp_line.length; m++) {
        for (let n = 0; n < ast.edges.length; n++) {
          if(ast.edges[n].source==temp_line[m])
          node_count[ast.edges[n].source]+=node_count[ast.edges[n].target];
        }
        if(node_count[temp_line[m]]==0)
        node_count[temp_line[m]]=1;
      }
    }
    return node_count;
  }


  //start[]和end[]数组，存储每一个节点以及子节点构成的子树所对应的开始位置和结束位置
  function getStart(ast){
    let line=getLine(ast);
    let node_count=getCount(ast);
    let nodes_line=getNodesLine(ast);
    let first_node=getFirstNode(ast);
    let father=[];
    for (let index = first_node.length-1; index >= 0; index--) {
      father[first_node[index]]=index;
    }
    let startX=[];
    let endX=[];
    let startY=[];
    startX[0]=0;
    endX[0]=node_count[0]*90;
    startY[0]=10;
    for (let index = 1; index < line.length; index++) {
      let temp_line=line[index];
      for(let m=0;m<temp_line.length;m++){
        let up_node=father[temp_line[m]];

        if(up_node!=null){
          startX[temp_line[m]]=startX[up_node];
          endX[temp_line[m]]=startX[temp_line[m]]+node_count[temp_line[m]]*90;
          startY[temp_line[m]]=nodes_line[temp_line[m]]*90+10;
        }
        else{
          startX[temp_line[m]]=endX[temp_line[m-1]];
          endX[temp_line[m]]=startX[temp_line[m]]+node_count[temp_line[m]]*90;
          startY[temp_line[m]]=nodes_line[temp_line[m]]*90+10;
        }
      }
      //console.log(startY);
    }
    return {startX,startY};

  }


//this.getStart(ast);


function drawAST(ast){
  let start=getStart(ast);
  let node_count=getCount(ast);
  let realStartX=[];
  let realStartY=[];
  for (let index = 0; index < ast.nodes.length; index++) {
    realStartX[index]=start.startX[index]+node_count[index]*45-5;
    realStartY[index]=start.startY[index];
    let line="line:"+ast.nodes[index].line;
    let type="type:"+ast.nodes[index].type;
    let label="label:"+ast.nodes[index].label;
    let normalized="normalized:"+ast.nodes[index].normalized;
    drawTextItem({startX:realStartX[index],startY:realStartY[index],
      width:80,height:60,line:line,type:type,label:label,normalized:normalized});
  }
  for (let index = 0; index < ast.edges.length; index++) {
    let source=ast.edges[index].source;
    let target=ast.edges[index].target;
    drawLine(realStartX[source],realStartY[source],realStartX[target],realStartY[target]);
  }
}

export{
    /* getCtx,
    drawTextItem,
    drawLine,
    getNodesLine,
    getFirstNode,
    getLine,
    getNextNodes,
    getNodes,
    getCount,
    getStart, */
    drawAST
}

/* 
        "line": 4,
        "type": "BLOCK",
        "label": "BLOCK",
        "normalized": "BLOCK" 
        */
//this.drawAST();

//let title="type:"+ast.nodes[1].type;
//console.log(title);




/* 
  //max_count[]对于每一个节点的子树中最多节点的一行的节点数
  function getMaxCount(node){
    let nodes=getNodes(node);
    let nodes_line=getNodesLine(ast);
    let max_line=0;
    for (let index = 0; index < nodes_line.length; index++) {
      if(nodes_line[index]>max_line)
      max_line=nodes_line[index];
    }
    let max_count=0;
    let count=[];
    for (let index = 0; index < max_line+1; index++) {
      count[index]=0;
    }
    for (let index = 0; index < nodes.length; index++) {
      let line=nodes_line[nodes[index]];
      count[line]++;
      if(count[line]>max_count)
      max_count=count[line];
    }
    console.log(max_count);
  }

  this.getMaxCount(18); 
  */
  
//this.drawTextItem({startX:40,startY:40,width:60,height:30,title:ast.nodes[0].type});