


function getCfg(ddg){

  let cfg={
    "nodes":[],
    "edges":[],
  };

    cfg.nodes=ddg.nodes;
    for (let index = 0; index < ddg.edges.length; index++) {
        if(ddg.edges[index].type== "Control")
        cfg.edges[index]=ddg.edges[index];
    }
    return cfg;
}



//getCfg(ddg,cfg);

let start_x=[];
let start_y=[];

let nw = 100;  //nodeWidth
let nh = 80;   //nodeHeight
let nm = 20;   //nodeMargin


  function getCtx(){
    let cs = document.getElementById('canvas');
    let ctx = cs.getContext('2d');
    return ctx;
    }


    
  //绘制节点框函数
  function drawTextItem({ startX, startY, width, height, id, line, label, defs, uses }) {
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
    let { width:textWidth1 } = ctx.measureText(id);
    let { width:textWidth2 } = ctx.measureText(line);
    let { width:textWidth3 } = ctx.measureText(label);
    let { width:textWidth4 } = ctx.measureText(defs);
    let { width:textWidth5 } = ctx.measureText(uses);

    if(defs=="defs:[ undefined ]")
    defs=" ";
    if(uses=="uses:[ undefined ]")
    uses=" ";
    
    ctx.fillText(id, startX+(width-textWidth1)/2, startY+height/2-30, textWidth1);
    ctx.fillText(line, startX+(width-textWidth2)/2, startY+height/2-15, textWidth2);
    ctx.fillText(label, startX+(width-textWidth3)/2, startY+height/2, textWidth3);
    ctx.fillText(defs, startX+(width-textWidth4)/2, startY+height/2+15, textWidth4);
    ctx.fillText(uses, startX+(width-textWidth5)/2, startY+height/2+30, textWidth5);
     }

     //this.drawTextItem({startX:10,startY:10,width:70,height:50,title:"title",line:"0",label:"label"})
  
  //绘制向下的连接线函数
  function drawLine1(startX,startY,endX,endY,label){
    let ctx = getCtx();
    //let nodeWidth = 80;
    //let nodeHeight = 60;

    //直线连接
    ctx.beginPath();
    ctx.moveTo(startX,startY);
    ctx.lineTo(endX,endY);
    ctx.stroke();

    ctx.fillStyle = "black";
    ctx.textBaseline = "middle";
    let { width:textWidth } = ctx.measureText(label);
    if(startX>endX)
    ctx.fillText(label, (startX+endX)/2-30,(startY+endY)/2, textWidth);
    else
    ctx.fillText(label, (startX+endX)/2+5,(startY+endY)/2, textWidth);
    }

  //绘制返回向上的连接线(循环的情况)
  function drawLine2(startX,startY,endX,endY,length,label){
    let ctx = getCtx();

    //直线连接
    ctx.beginPath();
    ctx.moveTo(startX,startY);
    ctx.lineTo(startX-length,startY);
    ctx.lineTo(startX-length,endY);
    ctx.lineTo(endX,endY);
    ctx.stroke();

    ctx.fillStyle = "black";
    ctx.textBaseline = "middle";
    let { width:textWidth } = ctx.measureText(label);
    ctx.fillText(label, startX-25,startY-10, textWidth);
    
    }


    //对于for和while循环，有由关键字指向end的一条边
    function drawLine3(startX,startY,endX,endY,length,label){
      let ctx = getCtx();
  
      //直线连接
      ctx.beginPath();
      ctx.moveTo(startX,startY);
      ctx.lineTo(startX+length,startY);
      ctx.lineTo(startX+length,endY);
      ctx.lineTo(endX,endY);
      ctx.stroke();
      

      ctx.fillStyle = "black";
      ctx.textBaseline = "middle";
      let { width:textWidth } = ctx.measureText(label);
      ctx.fillText(label, startX+5,startY-10, textWidth);
      }



    //每个节点框长为80，高为60，上下距离为20，左右距离为20



    //判断得到所有会出现分岔的节点
    function getFork(cfg){
        let fork=[];
        let i=0;
        for (let index = 0; index < cfg.edges.length; index++) {
          if(cfg.edges[index].label=="True"){
          fork[i]=cfg.edges[index].source;
          i++;
        }
        }
        //console.log(fork);
        return fork;
      }

//console.log(getFork(cfg));


      function getTry(cfg){
        let tcf=[];//try-catch-finally
        for (let index = 0; index < cfg.nodes.length; index++) {
          let label=cfg.nodes[index].label;
          if(label=="try")
          tcf[index]=1;
          else if(label.includes("catch") && label!="end-catch")
          tcf[index]=2;
          else if(label=="finally")
          tcf[index]=3;
        }
        return tcf;
      }




    //判断当前会出现分岔的结点所对应的关键字是什么
    //if记为1  while记为2  do-while记为3  for记为4  switch记为5
    function getJudge(cfg){
        //表示所有会产生分岔的结点
        let fork=getFork(cfg);
        //判断关键字，结果通过不同的数字表示
        //if记为1  while记为2  do-while记为3  for记为4  case记为5
        //比如，第5个节点代表的是do-while循环，则judge[5]=3
        let judge=[];
        for (let index = 0; index < fork.length; index++) {
          let node_label=cfg.nodes[fork[index]].label;
          if(node_label.search("if")!=-1){
            judge[fork[index]]=1;
          }
          //对于do-while语句，出现分岔的是while，但是关键字要改为do
          else if(node_label.search("while")!=-1){
            let last_label=cfg.nodes[fork[index]-1].label;
            if(last_label=="do"){
            judge[fork[index]-1]=3;
            //judge[fork[index]]=3;
            }
            else
            judge[fork[index]]=2;
          }
          else if(node_label.search("for")!=-1){
            judge[fork[index]]=4;
          }
        }

        for (let index = 0; index < cfg.nodes.length; index++) {
          let n_label=cfg.nodes[index].label;
          if(n_label.includes("switch") && n_label!="end-switch")
          judge[index]=5;
          else if(n_label=="try")
          judge[index]=6;
          else if(n_label.includes("catch") && n_label!="end-catch"){
            judge[index]=7;
          }
          else if(n_label=="finally")
          judge[index]=8;
        }
        //console.log(judge);
        return judge;
    }

//console.log(getJudge(cfg));

    //获取到当前所有关键字所对应的end节点的位置
    function getEnd(cfg){
        //表示所有会产生分岔的结点
        let judge=getJudge(cfg);
        //所有关键字所对应的end节点
        let end=[];
        for (let index = 0; index < judge.length; index++) {
          let t=judge[index];
          //if语句关键字对应的end
          if(t==1){
              let temp=0;
              for (let m = index+1; m < cfg.nodes.length; m++) {
                let temp_label=cfg.nodes[m].label;
                if(temp_label.search("if")!=-1 && temp_label.search("endif")==-1){
                  temp++;}

                if(temp_label.search("endif")!=-1){
                  if(temp==0)
                  {
                    end[index]=m;
                    break;
                  }
                  else
                  {
                    temp--;
                  }
                }
              }
            }
          //while语句关键字对应的end
          else if(t==2){
              for (let m = index+1; m < cfg.nodes.length; m++) {
                let temp_label=cfg.nodes[m].label;
                if(temp_label.search("endwhile")!=-1)
                {
                  end[index]=m;
                  break;
                }
              }
            }
          //do-while语句关键字对应的end
          else if(t==3){
              for (let m = index+1; m < cfg.nodes.length; m++) {
                let temp_label=cfg.nodes[m].label;
                if(temp_label.search("end-do-while")!=-1)
                {
                  end[index]=m;
                  break;
                }
              }
            }
          //for语句关键字对应的end
          else if(t==4){
              for (let m = index+1; m < cfg.nodes.length; m++) {
                let temp_label=cfg.nodes[m].label;
                if(temp_label.search("endfor")!=-1)
                {
                  end[index]=m;
                  break;
                }
              }
            }
          //switch语句关键字对应的end
          else if(t==5){
            for (let m = index+1; m < cfg.nodes.length; m++) {
              let temp_label=cfg.nodes[m].label;
              if(temp_label=="end-switch")
              {
                end[index]=m;
                break;
              }
            }
            }
          else if(t==6){
            end[index]=index+1;
          }
          else if(t==7){
            for (let m = index; m > 0; m--) {
              let temp_label=cfg.nodes[m].label;
              if(temp_label=="end-catch")
              {
                end[index]=m;
                break;
              }
            }
          }
          else if(t==8){
            for (let m = index+1; m < cfg.nodes.length; m++) {
              let temp_label=cfg.nodes[m].label;
              if(temp_label.search("end-finally")!=-1)
              {
                end[index]=m;
                break;
              }
            }
          }

        }
        //console.log(end);
        return end;
    }

//console.log(getEnd(cfg));
    //this.getEnd(cfg);


    //获取在循环语句中循环体的最后一句，经由他返回新一轮循环的开始节点
    function getBack(cfg){
      let back=[];
      let judge=getJudge(cfg);
      let i=0;
      for (let index = 0; index < cfg.edges.length; index++) {
        let node_id=cfg.edges[index].target;
        let key=judge[node_id];
        if(key!=null){
          if(cfg.edges[index].source>=cfg.edges[index].target)
          {back[i]=cfg.edges[index].source;
          i++;}
        }
      }
      //console.log(back);
      return back;
    }

    //this.getBack(cfg);


    function getBreAndCon(cfg){
      let bre_next=[];
      let con_next=[];
      for (let index = 0; index < cfg.edges.length; index++) {
        if(cfg.nodes[cfg.edges[index].source].label.includes("break")){
          bre_next[cfg.edges[index].source]=cfg.edges[index].target;
        }
        else if(cfg.nodes[cfg.edges[index].source].label.includes("continue")){
          con_next[cfg.edges[index].source]=cfg.edges[index].target;
        }
      }
      return {bre_next,con_next};
    }
  
  //console.log(getBreAndCon(cfg));

    function getCase(cfg){
      let s_case=[],i=0;
      for (let index = 0; index < cfg.nodes.length; index++) {
        if(cfg.nodes[index].label.includes("case"))
        s_case[i++]=index;
        else if(cfg.nodes[index].label.includes("default"))
        s_case[i++]=index;
      }
      return s_case;
    }

    
    function getSpecial(cfg){
      let special=[];
      let judge=getJudge(cfg);
      let s_case=getCase(cfg);
      let key1=[];
      let i=0;
      for (let index = 0; index < judge.length; index++) {
        if(judge[index]!=null)
        {key1[i]=index;
        i++;}
      }
      let end=getEnd(cfg);
      let key2=[];
      let j=0;
      for (let index = 0; index < end.length; index++) {
        if(judge[index]!=null)
        {key2[j]=end[index];
        j++;}
      }
      //let judge=getJudge(cfg);
      let do_while=[];
      let t=0;
      for (let index = 0; index < judge.length; index++) {
        if(judge[index]==3){
          do_while[t++]=index+1;
        }
      }
      special=[...key1,...key2,...do_while,...s_case];
      //console.log(special);
      return special;
    }

     //this.getSpecial(cfg);
//console.log(getSpecial(cfg));



     //获取每一个函数的开始节点和结束节点
     //所谓的结束节点使用的是edge中没有下一个节点的点，是逻辑意义上的结束点
     function getFunStartAndFunEnd(cfg){
      let have_start=[];
      let have_next=[];
      let fun_start=[];
      let i=0;
      let fun_end=[];
      let j=0;
      for (let index = 0; index < cfg.nodes.length; index++) {
        have_start[index]=0;
        have_next[index]=0;
      }
      for (let index = 0; index < cfg.edges.length; index++) {
        have_start[cfg.edges[index].target]=1;
        have_next[cfg.edges[index].source]=1;
      }
      for (let index = 0; index < have_next.length; index++) {
        if(have_start[index]==0)
        fun_start[i++]=index;
        if(have_next[index]==0)
        fun_end[j++]=index;
      }
      return {fun_start,fun_end};
    }
    
    //this.getFunStartAndFunEnd(cfg);
//console.log(getFunStartAndFunEnd(cfg));

     //获取顺序结构的开始节点
     function getSeqStart(cfg){
      let seq_start=[];
      let i=0;
      let special=getSpecial(cfg);
      for (let index = 0; index < special.length; index++) {
        let node=special[index];
        for (let m = 0; m < cfg.edges.length; m++) {
          if(node==cfg.edges[m].source)
          {
            let temp=cfg.edges[m].target;
            if(special.includes(temp)==false)
            {
              seq_start[i]=temp;
              i++;
            }
          }
        }
      }
      //console.log(seq_start);
      return seq_start;
     }


    //this.getSeqStart(cfg);
//console.log(getSeqStart(cfg));


//let countSeq=countSequence(cfg);
//let end=getEnd(cfg);
//let judge=getJudge(cfg);




//drawFun(6,400,10,cfg);

//console.log(start_x,start_y);
//drawIf(46,300,10,countSeq,end,judge,cfg);
//drawWhileOrFor(71,300,10,countSeq,end,judge,cfg);

    //计算每个顺序结构（以开始节点标记）的数量和他们对应的下一个结构的节点
    function countSequence(cfg){
      let special=getSpecial(cfg);
      let start=getSeqStart(cfg);
      let bre_con=getBreAndCon(cfg);
      let fun=getFunStartAndFunEnd(cfg);
      let count=[];
      let next=[];
      let bre=[];

      let judge=getJudge(cfg);
      let do_while=[];
      let t=0;
      for (let index = 0; index < judge.length; index++) {
        if(judge[index]==3){
          do_while[t++]=index+1;
        }
      }
      //正式使用的时候要改为0，保证是函数定义的语句
      start=[...start,...fun.fun_start];
      for (let index = 0; index < start.length; index++) {
        let sta=start[index];
        let cou=0;
        let temp=sta;

        for (let m = 0; m < cfg.edges.length; m++) {
          if(cfg.edges[m].source==temp){
            if(bre_con.con_next[temp]){
              next[sta]=cfg.edges[m].target;
              bre[sta]=temp;
              continue;
            }
            if(bre_con.bre_next[temp]){
              bre[sta]=temp;
            }
            let tar=cfg.edges[m].target;
            /* if(do_while.includes(tar)){
              next[sta]=tar;
            }
            else{ cou++;
              temp=tar;}
            */
           
            if(special.includes(tar)==false){
              cou++;
              temp=tar;
            }
            else{
              next[sta]=tar;
            }
          }
        }
        count[sta]=cou;
      }
 /*      
      let fun=getFunStartAndFunEnd(cfg);
      for (let index = 0; index < fun.fun_start.length; index++) {
        let sta=fun.fun_start[index];
        let cou=0;
        let temp=sta;
        for (let m = 0; m < cfg.edges.length; m++) {
          if(cfg.edges[m].source==temp){
            let tar=cfg.edges[m].target;
            if(special.includes(tar)==false){
              cou++;
              temp=tar;
            }
            else{
              next[sta]=tar;
            }
            if(fun.fun_end[temp]){
              break;
            }
          }
        }
        count[sta]=cou;
      }  */
      
      
      //console.log(count,next);
      //count[9]=1;
      //next[9]=11;
      return {count,next,bre};
    }

//console.log(countSequence(cfg));
    //this.countSequence(cfg);

    function countIf(node,countSeq,end,judge,cfg){
      let countX=40+20;
      let countY=(nh+nm)*2;
      let leftX=0,leftY=0;
      let rightX=0,rightY=0;
      for (let index = 0; index < cfg.edges.length; index++) {
        //判断为true的一侧
        if(cfg.edges[index].source==node && cfg.edges[index].label=="True"){
          let new_start=cfg.edges[index].target;
          let count;
          //是一个特殊的关键字
          for (let m = 0; m < cfg.edges.length; m++) {
            if(new_start==end[node]) {break;}
                if(judge[new_start])
              {
                if(judge[new_start]==1) count=countIf(new_start,countSeq,end,judge,cfg);
                else if(judge[new_start]==2) count=countWhileOrFor(new_start,countSeq,end,judge,cfg);
                else if(judge[new_start]==3) count=countDo(new_start,countSeq,end,judge,cfg);
                else if(judge[new_start]==4) count=countWhileOrFor(new_start,countSeq,end,judge,cfg);
                else if(judge[new_start]==5) count=countSwitch(new_start,countSeq,end,judge,cfg);
                else if(judge[new_start]==6) {count=countTry(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==8) {count=countTryOrCatOrFin(new_start,countSeq,end,judge,cfg);}
                leftX=Math.max(leftX,count.countX);
                leftY+=count.countY;
                for (let n = 0; n < cfg.edges.length; n++) {
                  if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                  new_start=cfg.edges[n].target;
                  break;}
                }
              }            
              //是基本的顺序结构
              if(countSeq.count[new_start]!=null){
                leftX=Math.max(leftX,nw);
                leftY+=(countSeq.count[new_start]+1)*(nh+nm);  
                if(countSeq.bre[new_start])
                break;
                //new_start=new_start+countSeq.count[new_start]+1;
                else
                new_start=countSeq.next[new_start];
              }
          }
        }
        //判断为false的一侧
        if(cfg.edges[index].source==node && cfg.edges[index].label=="False"){
          let new_start=cfg.edges[index].target;
          let count;
          //是一个特殊的关键字
          for (let m = 0; m < cfg.edges.length; m++) {
            if(new_start==end[node]) {break;}
                if(judge[new_start])
              {
                if(judge[new_start]==1) count=countIf(new_start,countSeq,end,judge,cfg);
                else if(judge[new_start]==2) count=countWhileOrFor(new_start,countSeq,end,judge,cfg);
                else if(judge[new_start]==3) count=countDo(new_start,countSeq,end,judge,cfg);
                else if(judge[new_start]==4) count=countWhileOrFor(new_start,countSeq,end,judge,cfg);
                else if(judge[new_start]==5) count=countSwitch(new_start,countSeq,end,judge,cfg);
                else if(judge[new_start]==6) {count=countTry(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==8) {count=countTryOrCatOrFin(new_start,countSeq,end,judge,cfg);}
                rightX=Math.max(rightX,count.countX);
                rightY+=count.countY;
                for (let n = 0; n < cfg.edges.length; n++) {
                  if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                  new_start=cfg.edges[n].target;
                  break;}
                }
              }
              //是基本的顺序结构
              if(countSeq.count[new_start]!=null){ 
                rightX=Math.max(rightX,nw);
                rightY+=(countSeq.count[new_start]+1)*(nh+nm);  
                if(countSeq.bre[new_start])
                break;
                //new_start=new_start+countSeq.count[new_start]+1;
                else
                new_start=countSeq.next[new_start];
              }
          }
        }
      }
      countX=countX+leftX+rightX;
      if(leftX==0 || rightX==0){
        countX+=nw-20;
      }
      countY=countY+Math.max(leftY,rightY);
      console.log("if",node,countX,countY,leftX,leftY,rightX,rightY);
      return {countX,countY,leftX,leftY,rightX,rightY};
    }



    function countWhileOrFor(node,countSeq,end,judge,cfg){
      let countX=40;
      let countY=(nh+nm)*2;
      let innerX=0,innerY=0;
      for (let index = 0; index < cfg.edges.length; index++) {
        if(cfg.edges[index].source==node && cfg.edges[index].label=="True"){
          let new_start=cfg.edges[index].target;
          let count;
          
          for (let m = 0; m < cfg.edges.length; m++) {
            if(new_start==node) {
              break;}
              //是一个特殊的关键字
              if(judge[new_start])
              {
                if(judge[new_start]==1) {count=countIf(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==2) {count=countWhileOrFor(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==3) {count=countDo(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==4) {count=countWhileOrFor(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==5) {count=countSwitch(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==6) {count=countTry(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==8) {count=countTryOrCatOrFin(new_start,countSeq,end,judge,cfg);}
                innerX=Math.max(innerX,count.countX);
                innerY=innerY+count.countY;
                for (let n = 0; n < cfg.edges.length; n++) {
                  if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                    new_start=cfg.edges[n].target;
                    break;
                  }
                }
                //break;
              }
              //是基本的顺序结构
              else if(countSeq.count[new_start]!=null){
                innerX=Math.max(innerX,nw);
                innerY+=(countSeq.count[new_start]+1)*(nh+nm);  
                new_start=countSeq.next[new_start];
              }
          }
          break;
        }
      }
      countX=countX+innerX;
      countY=countY+innerY;
      console.log("while or for",node,countX,countY);
      return {countX,countY};
    }

    //this.countWhileOrFor(20,cfg);

    function countDo(node,countSeq,end,judge,cfg){
      let countX=40;
      let countY=(nh+nm)*3;
      let innerX=0,innerY=0;
      for (let index = 0; index < cfg.edges.length; index++) {
        if(cfg.edges[index].source==node){
          let new_start=cfg.edges[index].target;
          let count;
          for (let m = 0; m < cfg.edges.length; m++) {
            if(new_start==node+1) {
              break;}
              //是一个特殊的关键字
              if(judge[new_start])
              {
                if(judge[new_start]==1) {count=countIf(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==2) {count=countWhileOrFor(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==3) {count=countDo(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==4) {count=countWhileOrFor(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==5) {count=countSwitch(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==6) {count=countTry(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==8) {count=countTryOrCatOrFin(new_start,countSeq,end,judge,cfg);}
                innerX=Math.max(innerX,count.countX);
                innerY=innerY+count.countY;
                for (let n = 0; n < cfg.edges.length; n++) {
                  if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                    new_start=cfg.edges[n].target;
                    break;
                  }
                }
                //break;
              }
              //是基本的顺序结构
              else if(countSeq.count[new_start]!=null){
                innerX=Math.max(innerX,80);
                innerY+=(countSeq.count[new_start]+1)*(nh+nm);  
                new_start=countSeq.next[new_start];
              }
          }
          break;
        }
      }
      countX=countX+innerX;
      countY=countY+innerY;
      console.log("do while",node,countX,countY);
      return {countX,countY};
    }


    //this.countDo(10,cfg);
    


    function 
    countCase(node,s_node,countSeq,end,judge,cfg){
      let countX=nw;
      let countY=nh;
      let innerX=0,innerY=0;

      let next,bre;
      for (let index = 0; index < cfg.edges.length; index++) {
        //关键字是case
        if(cfg.edges[index].source==node && cfg.edges[index].label=="True"){
          let new_start=cfg.edges[index].target;
          if(new_start!=node+1) break;
          let count;
          for (let m = 0; m < cfg.edges.length; m++) {
            //case指向false的结点
            if(cfg.edges[m].source==node && cfg.edges[m].label=="False"){
              next=cfg.edges[m].target;}
            //检索到break且能够直接指向此次switch的end关键字
            if(cfg.nodes[new_start].label=="break" && cfg.edges[m].target==end[s_node])
              bre=new_start;

            if(new_start==next) {
              break;}
            else if(new_start==bre){
              countY+=nh;
              break;
            }
              //是一个特殊的关键字
              if(judge[new_start])
              {
                if(judge[new_start]==1) {count=countIf(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==2) {count=countWhileOrFor(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==3) {count=countDo(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==4) {count=countWhileOrFor(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==5) {count=countSwitch(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==6) {count=countTry(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==8) {count=countTryOrCatOrFin(new_start,countSeq,end,judge,cfg);}
                innerX=Math.max(innerX,count.countX);
                innerY=innerY+count.countY;
                for (let n = 0; n < cfg.edges.length; n++) {
                  if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                    new_start=cfg.edges[n].target;
                    break;
                  }
                }
                //break;
              }
              //是基本的顺序结构
              else if(countSeq.count[new_start]!=null){
                innerX=Math.max(innerX,nw);
                innerY+=(countSeq.count[new_start]+1)*(nh+nm);  
                new_start=countSeq.next[new_start];
              }
          }
          break;
        }
          
      }
      countX=Math.max(countX,innerX);
      countY=countY+innerY;
      if(countY==nh*2)
      countY+=nm;
      else if(innerY==0)
      countY=nh+nm+nh;
      console.log("case",node,countX,countY);
      return {countX,countY,innerY};
    }

//this.countCase(37,32,cfg);


    function countDefault(node,s_end,countSeq,end,judge,cfg){
      let countX=nw;
      let countY=nh;
      let innerX=0,innerY=0;

      for (let index = 0; index < cfg.edges.length; index++) {
        //关键字是default
        if(cfg.edges[index].source==node && cfg.nodes[cfg.edges[index].source].label=="default:"){
          let new_start=cfg.edges[index].target;
          
          let count;
          for (let m = 0; m < cfg.edges.length; m++) {

            if(new_start==s_end) break;
            
              //是一个特殊的关键字
              if(judge[new_start])
              {
                if(judge[new_start]==1) {count=countIf(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==2) {count=countWhileOrFor(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==3) {count=countDo(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==4) {count=countWhileOrFor(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==5) {count=countSwitch(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==6) {count=countTry(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==8) {count=countTryOrCatOrFin(new_start,countSeq,end,judge,cfg);}
                innerX=Math.max(innerX,count.countX);
                innerY=innerY+count.countY;
                for (let n = 0; n < cfg.edges.length; n++) {
                  if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                    new_start=cfg.edges[n].target;
                    break;
                  }
                }
                //break;
              }
              //是基本的顺序结构
              else if(countSeq.count[new_start]!=null){
                innerX=Math.max(innerX,nw);
                innerY+=(countSeq.count[new_start]+1)*(nh+nm);  
                new_start=countSeq.next[new_start];
              }
          }
          break;
        }
          
      }
      countX=Math.max(countX,innerX);
      countY=countY+innerY;
      console.log("default",node,countX,countY);
      return {countX,countY};
    }
    
//this.countDefault(28,23,cfg);

    function countSwitch(node,countSeq,end,judge,cfg){
      let s_case=getCase(cfg);
      let countX=nw;
      let countY=(nh+nm)*2+20;
      //let innerX=0,innerY=0;

      let max=0;
      for (let index = 0; index < cfg.edges.length; index++) {
        if(cfg.edges[index].target==end[node]){
          if(cfg.edges[index].source>max)
          max=cfg.edges[index].source;
        }
      }

      let i=0;
      for (let index = 0; index < s_case.length; index++) {
        let temp=s_case[index];
        if(temp>node){
          i++;
          if(temp>max) break;
          let count;
          if(i==1){
            if(cfg.nodes[temp].label.includes("case")){
              count=countCase(temp,node,countSeq,end,judge,cfg);
              countX=Math.max(count.countX,countX)/2+20;
              countY+=count.countY;
            }
            if(cfg.nodes[temp].label.includes("default")){
              count=countDefault(temp,end[node],countSeq,end,judge,cfg);
              countX=Math.max(count.countX,countX)/2;
              countY+=count.countY;
            }
          }
          else{
            if(cfg.nodes[temp].label.includes("case")){
              count=countCase(temp,node,countSeq,end,judge,cfg);
              countX+=count.countX+20;
              countY+=count.countY-nh;
            }
            else if(cfg.nodes[temp].label.includes("default")){
              count=countDefault(temp,end[node],countSeq,end,judge,cfg);
              countX+=count.countX;
              countY+=count.countY-nh;
            }
          }
          
        }
        
    }


      
      countX=countX*2;
      console.log("switch",node,countX,countY);
      return {countX,countY};
    }

//countSwitch(1,cfg);


    function countTryOrCatOrFin(node,countSeq,end,judge,cfg){

      let countX=nw;
      let countY=0;
      if(judge[node]==6)
      countY=(nh+nm)*2;
      else if(judge[node]==7)
      countY=(nh+nm);
      else if(judge[node]==8)
      countY=(nh+nm)*2;
      let innerX=0,innerY=0;

      for (let index = 0; index < cfg.edges.length; index++) {
        //关键字是catch
        if(cfg.edges[index].source==node){
          let new_start=cfg.edges[index].target;
          
          let count;
          for (let m = 0; m < cfg.edges.length; m++) {

            if(new_start==end[node]) break;
            
              //是一个特殊的关键字
              if(judge[new_start])
              {
                if(judge[new_start]==1) {count=countIf(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==2) {count=countWhileOrFor(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==3) {count=countDo(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==4) {count=countWhileOrFor(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==5) {count=countSwitch(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==6) {count=countTry(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==8) {count=countTryOrCatOrFin(new_start,countSeq,end,judge,cfg);}
                innerX=Math.max(innerX,count.countX);
                innerY=innerY+count.countY;
                for (let n = 0; n < cfg.edges.length; n++) {
                  if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                    new_start=cfg.edges[n].target;
                    break;
                  }
                }
                //break;
              }
              //是基本的顺序结构
              else if(countSeq.count[new_start]!=null){
                innerX=Math.max(innerX,nw);
                innerY+=(countSeq.count[new_start]+1)*(nh+nm);  
                new_start=countSeq.next[new_start];
              }
          }
          break;
        }
          
      }
      countX=Math.max(countX,innerX);
      countY=countY+innerY;
      if(judge[node]==6)
      console.log("try inner",node,countX,countY);
      else if(judge[node]==7)
      console.log("catch",node,countX,countY);
      else if(judge[node]==8)
      console.log("finally",node,countX,countY);
      return {countX,countY};
    }

  
//countTryOrCatOrFin(2,countSeq,end,judge,cfg);

    function countTry(node,countSeq,end,judge,cfg){
      let count=countTryOrCatOrFin(node,countSeq,end,judge,cfg);
      let countX=count.countX;
      let countY=count.countY;

      let innerX=0,innerY=0;

      //catch语句的部分
      let cat=[],i=0,cat_count=[],j=0;
      for (let index = 0; index < cfg.edges.length; index++) {
            if(cfg.edges[index].source==end[node] && cfg.edges[index].label=="Throws"){
            cat[i++]=cfg.edges[index].target;
            cat_count[j]=countTryOrCatOrFin(cat[j],countSeq,end,judge,cfg);
            j++;
          }
      }
      for (let index = 0; index < cat_count.length; index++) {
        innerX+=cat_count[index].countX+20;
        innerY=Math.max(innerY,cat_count[index].countY);
      }
      if(innerX!=0){
      innerX-=20;
      countY+=(nh+nm); //为end-catch增加的位置
    }
      countX=Math.max(countX,innerX);
      countY+=innerY;


      //finally语句的部分
      console.log("try",node,countX,countY);
      return {countX,countY,cat,cat_count};
    }

//countTry(2,countSeq,end,judge,cfg);
   


    function countFun(node,countSeq,end,judge,cfg){
      let fun=getFunStartAndFunEnd(cfg);console.log(fun);
      let countX=0;
      let countY=0;
      let innerX=0,innerY=0;
      for (let index = 0; index < cfg.edges.length; index++) {
        if(cfg.edges[index].source==node){
          let new_start=node;
          let count;
          for (let m = 0; m < cfg.edges.length; m++){
              //if(fun.fun_end.includes(new_start)) {break;}
              //是一个特殊的关键字
              if(judge[new_start])
              {
                if(judge[new_start]==1) {count=countIf(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==2) {count=countWhileOrFor(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==3) {count=countDo(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==4) {count=countWhileOrFor(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==5) {count=countSwitch(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==6) {count=countTry(new_start,countSeq,end,judge,cfg);}
                else if(judge[new_start]==8) {count=countTryOrCatOrFin(new_start,countSeq,end,judge,cfg);}
                innerX=Math.max(innerX,count.countX);
                innerY=innerY+count.countY;
                if(fun.fun_end.includes(end[new_start]))break;
                for (let n = 0; n < cfg.edges.length; n++) {
                    if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                      new_start=cfg.edges[n].target;
                      break;
                    }
                  }
                //break;
              }
              //是基本的顺序结构，被记载到的在特殊关键字之后的顺序语句
              else if(countSeq.count[new_start]!=null){
                innerX=Math.max(innerX,nw);
                innerY+=(countSeq.count[new_start]+1)*(nh+nm);  
                if(countSeq.next[new_start])
                  new_start=countSeq.next[new_start];
                else
                  break;
              }
          }
          break;
        }
      }
      countX=countX+innerX;
      //console.log(countY,innerY);
      countY=countY+innerY;
      console.log("function",node,countX,countY);
      return {countX,countY};
    }

    
    //this.countFun(44,countSeq,end,judge,cfg);

    //所有的x和y给的是最中间的点的坐标
    function drawSeq(node,x,y,countSeq,end,judge,cfg){
      let id;
      let line;
      let label;
      let defs;
      let uses;
      let fun=getFunStartAndFunEnd(cfg);

      if(fun.fun_end.includes(node)){
        id="id: "+cfg.nodes[node].id;
        line="line: "+cfg.nodes[node].line;
        label="label:"+cfg.nodes[node].label;
        defs="defs:[ "+cfg.nodes[node].defs+" ]";
        uses="uses:[ "+cfg.nodes[node].uses+" ]";

        drawTextItem({startX:x-nw/2,startY:y,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
        start_x[node]=x-nw/2;  
        start_y[node]=y;
      }
      
      let temp=node;
      for (let index = 0; index < cfg.edges.length; index++) {
        if(cfg.edges[index].source==temp){
          if(temp==countSeq.next[node]){
          break;}
          else{
            if(temp!=node)
            drawLine1(x,y-nm,x,y," ");
            id="id: "+cfg.nodes[temp].id;
            line="line: "+cfg.nodes[temp].line;
            label="label:"+cfg.nodes[temp].label;
            defs="defs:[ "+cfg.nodes[node].defs+" ]";
            uses="uses:[ "+cfg.nodes[node].uses+" ]";
            drawTextItem({startX:x-nw/2,startY:y,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
            start_x[temp]=x-nw/2;  
            start_y[temp]=y;
            y+=(nh+nm);
            temp=cfg.edges[index].target;
          }

          if(fun.fun_start.includes(node)){
            if(fun.fun_end.includes(temp) || temp==countSeq.next[node]){
              drawLine1(x,y-nm,x,y," ");
            id="id: "+cfg.nodes[temp].id;
            line="line: "+cfg.nodes[temp].line;
            label="label:"+cfg.nodes[temp].label;
            defs="defs:[ "+cfg.nodes[node].defs+" ]";
            uses="uses:[ "+cfg.nodes[node].uses+" ]";
            drawTextItem({startX:x-nw/2,startY:y,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
            start_x[temp]=x-nw/2;  
            start_y[temp]=y;
            break;
            }
            if(temp!=node)
            drawLine1(x,y-nm,x,y," ");
            id="id: "+cfg.nodes[temp].id;
            line="line: "+cfg.nodes[temp].line;
            label="label:"+cfg.nodes[temp].label;
            defs="defs:[ "+cfg.nodes[node].defs+" ]";
            uses="uses:[ "+cfg.nodes[node].uses+" ]";
            drawTextItem({startX:x-nw/2,startY:y,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
            start_x[temp]=x-nw/2;  
            start_y[temp]=y;
            y+=(nh+nm);
            temp++;
          }

          
          
        }
      }
      // && node<countSeq.next[node]
      if(judge[countSeq.next[node]] && countSeq.bre[node]==null)
      drawLine1(x,y-nm,x,y," ");
      let countX=nw;
      let countY=(countSeq.count[node]+1)*(nh+nm);
      return {countX,countY};
    }

   
    //console.log(countSeq);
//this.drawSeq(0,10,10,countSeq,end,judge,cfg);


function drawLineIfNull(startX,startY,endX,endY,length,label){
  let ctx = getCtx();

  //直线连接
  ctx.beginPath();
  ctx.moveTo(startX,startY);
  ctx.lineTo(startX+length,startY+nm);
  ctx.lineTo(startX+length,endY-nm);
  ctx.lineTo(endX,endY);
  ctx.stroke();

  ctx.fillStyle = "black";
  ctx.textBaseline = "middle";
  let { width:textWidth } = ctx.measureText(label);
  ctx.fillText(label, (startX+startX+length)/2,(startY+startY+nm)/2, textWidth);
  
  }

  function drawIf(node,x,y,countSeq,end,judge,cfg){
    let count=countIf(node,countSeq,end,judge,cfg);
    let leftX=x-count.countX/2+10+count.leftX/2,leftY=y+nh+nm+(count.countY-(nh+nm)*2-count.leftY)/2;
    let rightX=x-count.countX/2+10+count.leftX+20+count.rightX/2,rightY=y+nh+nm+(count.countY-(nh+nm)*2-count.rightY)/2;

    let id="id: "+cfg.nodes[node].id;
    let line="line: "+cfg.nodes[node].line;
    let label="label:"+cfg.nodes[node].label;
    let defs="defs:[ "+cfg.nodes[node].defs+" ]";
    let uses="uses:[ "+cfg.nodes[node].uses+" ]";
    //绘制if关键字的节点框
    drawTextItem({startX:x-nw/2,startY:y,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
    start_x[node]=x-nw/2;  
    start_y[node]=y;
    //绘制由开始的if节点指向左右分支的线段
    if(count.leftX!=0)
    drawLine1(x,y+nh,leftX,leftY,"True");
    else
    drawLineIfNull(x,y+nh,x,y+count.countY-nh-nm,count.countX/2-20-30,"True");
    if(count.rightX!=0)
    drawLine1(x,y+nh,rightX,rightY,"False");
    else
    drawLineIfNull(x,y+nh,x,y+count.countY-nh-nm,count.countX/2-20-30,"False");
    //绘制结束if关键字的节点框
    id="id: "+cfg.nodes[end[node]].id;
    line="line: "+cfg.nodes[end[node]].line;
    label="label:"+cfg.nodes[end[node]].label;
    defs="defs:[ "+cfg.nodes[node].defs+" ]";
    uses="uses:[ "+cfg.nodes[node].uses+" ]";
    drawTextItem({startX:x-nw/2,startY:y+count.countY-nh-nm,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
    start_x[end[node]]=x-nw/2;  
    start_y[end[node]]=y+count.countY-nh-nm;
    

    let l=0,r=0;

    for (let index = 0; index < cfg.edges.length; index++) {
      if(cfg.edges[index].source==end[node] && cfg.edges[index].source<cfg.edges[index].target){
        if(judge[cfg.edges[index].target] || countSeq.count[cfg.edges[index].target]!=null)
        drawLine1(x,y+count.countY-nm,x,y+count.countY,label=" ");
      }
      //判断为true的一侧
      if(cfg.edges[index].source==node && cfg.edges[index].label=="True"){
        let new_start=cfg.edges[index].target;
        let size;
        for (let m = 0; m < cfg.edges.length; m++) {
          if(new_start==end[node]) {
            break;}
          //是一个特殊的关键字
              if(judge[new_start])
            {
              if(judge[new_start]==1) size=drawIf(new_start,leftX,leftY,countSeq,end,judge,cfg);
              else if(judge[new_start]==2) size=drawWhileOrFor(new_start,leftX,leftY,countSeq,end,judge,cfg);
              else if(judge[new_start]==3) size=drawDo(new_start,leftX,leftY,countSeq,end,judge,cfg);
              else if(judge[new_start]==4) size=drawWhileOrFor(new_start,leftX,leftY,countSeq,end,judge,cfg);
              else if(judge[new_start]==5) size=drawSwitch(new_start,leftX,leftY,countSeq,end,judge,cfg);

              leftY+=size.countY;
              for (let n = 0; n < cfg.edges.length; n++) {
                if(cfg.edges[n].source==end[new_start]  && cfg.edges[n].label!="Throws"){
                new_start=cfg.edges[n].target;
                break;}
              }
            }            
            //是基本的顺序结构
            if(countSeq.count[new_start]!=null){
              size=drawSeq(new_start,leftX,leftY,countSeq,end,judge,cfg);
              leftY+=size.countY;
              if(countSeq.bre[new_start]){  
                l++;
                break;
              //new_start=new_start+countSeq.count[new_start]+1;
            }
              else
              new_start=countSeq.next[new_start];
            }
        }
      }
      //判断为false的一侧
      if(cfg.edges[index].source==node && cfg.edges[index].label=="False"){
        let new_start=cfg.edges[index].target;
        let size;
        for (let m = 0; m < cfg.edges.length; m++) {
          if(new_start==end[node]) {
            break;}
          //是一个特殊的关键字
          if(judge[new_start])
          {
              if(judge[new_start]==1) size=drawIf(new_start,rightX,rightY,countSeq,end,judge,cfg);
              else if(judge[new_start]==2) size=drawWhileOrFor(new_start,rightX,rightY,countSeq,end,judge,cfg);
              else if(judge[new_start]==3) size=drawDo(new_start,rightX,rightY,countSeq,end,judge,cfg);
              else if(judge[new_start]==4) size=drawWhileOrFor(new_start,rightX,rightY,countSeq,end,judge,cfg);
              else if(judge[new_start]==5) size=drawSwitch(new_start,rightX,rightY,countSeq,end,judge,cfg);

              rightY+=size.countY;
              for (let n = 0; n < cfg.edges.length; n++) {
                if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                new_start=cfg.edges[n].target;
                break;}
              }
            }
            //是基本的顺序结构
            if(countSeq.count[new_start]!=null){ 
              size=drawSeq(new_start,rightX,rightY,countSeq,end,judge,cfg);
              rightY+=size.countY;
              if(countSeq.bre[new_start]){  
                r++;
                break;
              //new_start=new_start+countSeq.count[new_start]+1;
            }
              else
              new_start=countSeq.next[new_start];
            }
        }
      }
    }

    //绘制由左右分支指向end结束节点的线段
    if(count.leftX!=0){
      if(l==0)
    drawLine1(leftX,leftY-nm,x,y+count.countY-nh-nm," ");}
    if(count.rightX!=0){
      if(r==0)
    drawLine1(rightX,rightY-nm,x,y+count.countY-nh-nm," ");}
    
    let countX=count.countX,countY=count.countY;
    return {countX,countY};
  }

    //let size=drawIf(22,10,10,countSeq,end,judge,cfg);
    //console.log(size);
    //this.drawIf(3,10,10,countSeq,end,judge,cfg);

    

    function drawWhileOrFor(node,x,y,countSeq,end,judge,cfg){
      let count=countWhileOrFor(node,countSeq,end,judge,cfg);


      let innerX=x;
      let innerY=y+nh+nm;
      
      let id="id: "+cfg.nodes[node].id;
      let line="line: "+cfg.nodes[node].line;
      let label="label:"+cfg.nodes[node].label;
      let defs="defs:[ "+cfg.nodes[node].defs+" ]";
      let uses="uses:[ "+cfg.nodes[node].uses+" ]";
      //绘制while关键字的节点框
      drawTextItem({startX:x-nw/2,startY:y,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
      start_x[node]=x-nw/2;  
      start_y[node]=y;
      //绘制由开始的while节点指向inner循环体的线段
      drawLine1(x,y+nh,x,y+nh+nm,"True");
      //绘制结束if关键字的节点框

      id="id: "+cfg.nodes[end[node]].id;
      line="line: "+cfg.nodes[end[node]].line;
      label="label:"+cfg.nodes[end[node]].label;
      defs="defs:[ "+cfg.nodes[node].defs+" ]";
      uses="uses:[ "+cfg.nodes[node].uses+" ]";
      drawTextItem({startX:x-nw/2,startY:y+count.countY-nh-nm,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
      start_x[end[node]]=x-nw/2;  
      start_y[end[node]]=y+count.countY-nh-nm;
      
      for (let index = 0; index < cfg.edges.length; index++) {
          if(cfg.edges[index].source==end[node] ){
            if(judge[cfg.edges[index].target] || countSeq.count[cfg.edges[index].target]!=null){
              
            drawLine1(x,y+count.countY-nm,x,y+count.countY,label=" ");
          }}
        if(cfg.edges[index].source==node && cfg.edges[index].label=="True"){
          let new_start=cfg.edges[index].target;
          let size;
          for (let m = 0; m < cfg.edges.length; m++) {
            if(new_start==node) {
              break;}
              //是一个特殊的关键字
              if(judge[new_start])
              {
                if(judge[new_start]==1) size=drawIf(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==2) size=drawWhileOrFor(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==3) size=drawDo(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==4) size=drawWhileOrFor(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==5) size=drawSwitch(new_start,innerX,innerY,countSeq,end,judge,cfg);
                innerY+=size.countY;
                for (let n = 0; n < cfg.edges.length; n++) {
                  if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                    new_start=cfg.edges[n].target;
                    break;
                  }
                }
                //break;
              }
              //是基本的顺序结构
              else if(countSeq.count[new_start]!=null){
                size=drawSeq(new_start,innerX,innerY,countSeq,end,judge,cfg);
                innerY+=size.countY;
                new_start=countSeq.next[new_start];
              }
          }
          //break;
        }
      }

      //由循环体结束返回while关键字
      drawLine2(innerX-nw/2,innerY-nm-nh/2,x-nw/2,y+nh/2,count.countX/2-nw/2," ");
      //由while直接指向end while
      drawLine3(x+nw/2,y+nh/2,x+nw/2,y+count.countY-nm-nh/2,count.countX/2-nw/2,"False");
      return count;
    }


    //this.drawWhileOrFor(17,10,10,countSeq,end,judge,cfg);

    function drawDo(node,x,y,countSeq,end,judge,cfg){
      let count=countDo(node,countSeq,end,judge,cfg);

      let innerX=x;
      let innerY=y+nh+nm;

      let id="id: "+cfg.nodes[node].id;
      let line="line: "+cfg.nodes[node].line;
      let label="label:"+cfg.nodes[node].label;
      let defs="defs:[ "+cfg.nodes[node].defs+" ]";
      let uses="uses:[ "+cfg.nodes[node].uses+" ]";
      //绘制do关键字的节点框
      drawTextItem({startX:x-nw/2,startY:y,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
      start_x[node]=x-nw/2;  
      start_y[node]=y;
      //绘制由开始的while节点指向inner循环体的线段
      drawLine1(x,y+nh,x,y+nh+nm," ");

      //绘制do while循环中的while节点框
      id="id: "+cfg.nodes[node+1].id;
      line="line: "+cfg.nodes[node+1].line;
      label="label:"+cfg.nodes[node+1].label;
      defs="defs:[ "+cfg.nodes[node].defs+" ]";
      uses="uses:[ "+cfg.nodes[node].uses+" ]";
      drawTextItem({startX:x-nw/2,startY:y+count.countY-(nh+nm)*2,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
      start_x[node+1]=x-nw/2;  
      start_y[node+1]=y+count.countY-(nh+nm)*2;

      //绘制由while关键字的上一个节点指向它的线段
      drawLine1(x,y+count.countY-(nh+nm)*2-nm,x,y+count.countY-(nh+nm)*2," ");
      //绘制由while节点指向end的线段
      drawLine1(x,y+count.countY-(nh+nm)-nm,x,y+count.countY-(nh+nm),"False");
      
      //绘制结束do while关键字的节点框
      id="id: "+cfg.nodes[end[node]].id;
      line="line: "+cfg.nodes[end[node]].line;
      label="label:"+cfg.nodes[end[node]].label;
      defs="defs:[ "+cfg.nodes[node].defs+" ]";
      uses="uses:[ "+cfg.nodes[node].uses+" ]";
      drawTextItem({startX:x-nw/2,startY:y+count.countY-nh-nm,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
      start_x[end[node]]=x-nw/2;  
      start_y[end[node]]=y+count.countY-nh-nm;
      
      for (let index = 0; index < cfg.edges.length; index++) {
        if(cfg.edges[index].source==end[node]){
          if(judge[cfg.edges[index].target] || countSeq.count[cfg.edges[index].target]!=null){
          drawLine1(x,y+count.countY-nm,x,y+count.countY,label=" ");
        }}
        if(cfg.edges[index].source==node){
          let new_start=cfg.edges[index].target;
          let size;
          for (let m = 0; m < cfg.edges.length; m++) {
            if(new_start==node+1) {
              break;}
              //是一个特殊的关键字
              if(judge[new_start])
              {
                if(judge[new_start]==1) size=drawIf(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==2) size=drawWhileOrFor(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==3) size=drawDo(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==4) size=drawWhileOrFor(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==5) size=drawSwitch(new_start,innerX,innerY,countSeq,end,judge,cfg);
                innerY+=size.countY;
                for (let n = 0; n < cfg.edges.length; n++) {
                  if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                    new_start=cfg.edges[n].target;
                    break;
                  }
                }
                //break;
              }
              //是基本的顺序结构
              else if(countSeq.count[new_start]!=null){
                size=drawSeq(new_start,innerX,innerY,countSeq,end,judge,cfg);
                innerY+=size.countY;
                new_start=countSeq.next[new_start];
              }
          }
          //break;
        }
      }
      //由while判断后返回do关键字
      drawLine2(x-nw/2,innerY+nh/2,x-nw/2,y+nh/2,count.countX/2-nw/2,"True");
      return count;
    }

    //this.drawDo(10,10,10,countSeq,end,judge,cfg);

    function drawCase(node,s_node,x,y,countSeq,end,judge,cfg){
      let count=countCase(node,s_node,countSeq,end,judge,cfg);

      let innerX=x;
      let innerY=y+nh+nm;
      
      let id="id: "+cfg.nodes[node].id;
      let line="line: "+cfg.nodes[node].line;
      let label="label:"+cfg.nodes[node].label;
      let defs="defs:[ "+cfg.nodes[node].defs+" ]";
      let uses="uses:[ "+cfg.nodes[node].uses+" ]";
      //绘制case关键字的节点框
      drawTextItem({startX:x-nw/2,startY:y,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
      start_x[node]=x-nw/2;  
      start_y[node]=y;

      if(count.innerY!=0)
      //绘制case向下的线段
      drawLine1(x,y+nh,x,y+nh+nm,"True");

      let next,bre;
      for (let index = 0; index < cfg.edges.length; index++) {
        if(cfg.edges[index].source==node && cfg.edges[index].label=="False"){
          if(cfg.edges[index].target==node+1)
          next=node+1;
        }
        //关键字是case
        if(cfg.edges[index].source==node && cfg.edges[index].label=="True"){
          let new_start=cfg.edges[index].target;
          
          if(new_start!=node+1) {
            if(new_start==end[s_node])
            next=new_start;
            break;}
          let size;
          for (let m = 0; m < cfg.edges.length; m++) {
            //case指向false的结点
            if(cfg.edges[m].source==node && cfg.edges[m].label=="False"){
              next=cfg.edges[m].target;}

            

            if(new_start==next || new_start==bre) {
              break;}
           
              //是一个特殊的关键字
              if(judge[new_start])
              {
                if(judge[new_start]==1) size=drawIf(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==2) size=drawWhileOrFor(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==3) size=drawDo(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==4) size=drawWhileOrFor(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==5) size=drawSwitch(new_start,innerX,innerY,countSeq,end,judge,cfg);
                innerY+=size.countY;
                for (let n = 0; n < cfg.edges.length; n++) {
                  if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                    new_start=cfg.edges[n].target;
                    break;
                  }
                }
                //break;
              }
             //是基本的顺序结构
             else if(countSeq.count[new_start]!=null){
              size=drawSeq(new_start,innerX,innerY,countSeq,end,judge,cfg);
              innerY+=size.countY;
              bre=countSeq.bre[new_start];
              new_start=countSeq.next[new_start];
            }
          }
          break;
        }
          
      }
      return {count,next,bre};
    }


  //drawCase(37,32,10,10,countSeq,end,judge,cfg);



  function drawDefault(node,s_end,x,y,countSeq,end,judge,cfg){
    let count=countDefault(node,s_end,countSeq,end,judge,cfg);

      let innerX=x;
      let innerY=y+nh+nm;
      
      let id="id: "+cfg.nodes[node].id;
      let line="line: "+cfg.nodes[node].line;
      let label="label:"+cfg.nodes[node].label;
      let defs="defs:[ "+cfg.nodes[node].defs+" ]";
      let uses="uses:[ "+cfg.nodes[node].uses+" ]";
      //绘制default关键字的节点框
      drawTextItem({startX:x-nw/2,startY:y,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
      start_x[node]=x-nw/2;  
      start_y[node]=y;

      if(count.countY!=nh)
      //绘制default向下的线段
      drawLine1(x,y+nh,x,y+nh+nm," ");

    for (let index = 0; index < cfg.edges.length; index++) {
      //关键字是case
      if(cfg.edges[index].source==node && cfg.nodes[cfg.edges[index].source].label=="default:"){
        let new_start=cfg.edges[index].target;
        
        let size;
        for (let m = 0; m < cfg.edges.length; m++) {

          if(new_start==s_end) break;
          
            //是一个特殊的关键字
            if(judge[new_start])
            {
              if(judge[new_start]==1) size=drawIf(new_start,innerX,innerY,countSeq,end,judge,cfg);
              else if(judge[new_start]==2) size=drawWhileOrFor(new_start,innerX,innerY,countSeq,end,judge,cfg);
              else if(judge[new_start]==3) size=drawDo(new_start,innerX,innerY,countSeq,end,judge,cfg);
              else if(judge[new_start]==4) size=drawWhileOrFor(new_start,innerX,innerY,countSeq,end,judge,cfg);
              else if(judge[new_start]==5) size=drawSwitch(new_start,innerX,innerY,countSeq,end,judge,cfg);
              innerY+=size.countY;
              for (let n = 0; n < cfg.edges.length; n++) {
                if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                  new_start=cfg.edges[n].target;
                  break;
                }
              }
              //break;
            }
           //是基本的顺序结构
           else if(countSeq.count[new_start]!=null){
            size=drawSeq(new_start,innerX,innerY,countSeq,end,judge,cfg);
            innerY+=size.countY;
            new_start=countSeq.next[new_start];
          }
        }
        break;
      }
        
    }
    
    return count;
  }

//drawDefault(18,2,10,10,countSeq,end,judge,cfg);


//case指向下一个case的转折指向线段
function drawLineC1(startX,startY,endX,endY,label){
  let ctx = getCtx();

  //直线连接
  ctx.beginPath();
  ctx.moveTo(startX,startY);
  ctx.lineTo(endX,startY);
  ctx.lineTo(endX,endY);
  ctx.stroke();

  ctx.fillStyle = "black";
  ctx.textBaseline = "middle";
  let { width:textWidth } = ctx.measureText(label);
  ctx.fillText(label, startX+10,startY-10, textWidth);
  
  }


  function drawLineC2(startX,startY,endX,endY,label){
    let ctx = getCtx();
  
    //直线连接
    ctx.beginPath();
    ctx.moveTo(startX,startY);
    ctx.lineTo(startX,endY);
    ctx.lineTo(endX,endY);
    ctx.stroke();
  
    ctx.fillStyle = "black";
    ctx.textBaseline = "middle";
    let { width:textWidth } = ctx.measureText(label);
    ctx.fillText(label, startX+10,startY+10, textWidth);
    
    }


    function drawLineCaseEnd(startX,startY,endX,endY,length,label){
      let ctx = getCtx();
    
      //直线连接
      ctx.beginPath();
      ctx.moveTo(startX,startY);
      ctx.lineTo(startX+length,startY);
      ctx.lineTo(startX+length,endY);
      ctx.lineTo(endX,endY);
      ctx.stroke();
    
      ctx.fillStyle = "black";
      ctx.textBaseline = "middle";
      let { width:textWidth } = ctx.measureText(label);
      ctx.fillText(label, startX+10,startY-10, textWidth);
      
      }



    //对于case为空的情况求其需要指向其后的几个节点的数量
  function getCaseNext(cfg){
      let s_case=getCase(cfg);
      let i=0;
      let ca_count=[];

    for (let index = 0; index < cfg.edges.length; index++) {
      if(cfg.edges[index].source==s_case[i] && cfg.edges[index].label=="True"){
        let tar=cfg.edges[index].target;
        if(tar!=s_case[i]+1)
        ca_count[s_case[i]]=tar;
        i++;
      }
    }

      return ca_count;
  }

  
  function drawSwitch(node,x,y,countSeq,end,judge,cfg){
    let count=countSwitch(node,countSeq,end,judge,cfg);
    let s_case=getCase(cfg);
    let ca_count=getCaseNext(cfg);

    let innerX=x;
    let innerY=y+nh+nm;

      let id="id: "+cfg.nodes[node].id;
      let line="line: "+cfg.nodes[node].line;
      let label="label:"+cfg.nodes[node].label;
      let defs="defs:[ "+cfg.nodes[node].defs+" ]";
      let uses="uses:[ "+cfg.nodes[node].uses+" ]";
      //绘制switch关键字的节点框
      drawTextItem({startX:x-nw/2,startY:y,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
      start_x[node]=x-nw/2;  
      start_y[node]=y;
      drawLine1(x,y+nh,x,y+nh+nm," ");

      //绘制结束的节点框
      id="id: "+cfg.nodes[end[node]].id;
      line="line: "+cfg.nodes[end[node]].line;
      label="label:"+cfg.nodes[end[node]].label;
      defs="defs:[ "+cfg.nodes[node].defs+" ]";
      uses="uses:[ "+cfg.nodes[node].uses+" ]";
      drawTextItem({startX:x-nw/2,startY:y+count.countY-nh-nm,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
      start_x[end[node]]=x-nw/2;  
      start_y[end[node]]=y+count.countY-nh-nm;
      
      let max=0;
      for (let index = 0; index < cfg.edges.length; index++) {
        if(cfg.edges[index].source==end[node]){
          if(judge[cfg.edges[index].target] || countSeq.count[cfg.edges[index].target]!=null){
          drawLine1(x,y+count.countY-20,x,y+count.countY,label=" ");
        }}
        if(cfg.edges[index].target==end[node]){
          if(cfg.edges[index].source>max)
          max=cfg.edges[index].source;
        }
      }
      

      let i=0;
      for (let index = 0; index < s_case.length; index++) {
        let temp=s_case[index];
        if(temp>node){
          i++;
          if(temp>max) break;
          let size,next_count;
          if(i==1){
            if(cfg.nodes[temp].label.includes("case")){
              size=drawCase(temp,node,innerX,innerY,countSeq,end,judge,cfg);
              if(size.next){
                  if(cfg.nodes[size.next].label.includes("case")){
                    next_count=countCase(size.next,node,countSeq,end,judge,cfg);
                    drawLineC1(innerX+nw/2,innerY+nh/2,innerX+size.count.countX/2+20+next_count.countX/2,innerY+size.count.countY-nh,"False");
                    if(size.count.innerY==0){
                      let cc=ca_count[temp]-temp;
                      drawLineC2(innerX,innerY+nh,innerX+(cc-1)*(nw+20)-nw/2,innerY+nh+cc*(nh+nm)-nh/2,"True");
                    }
                    else if(size.bre==null){
                      drawLine1(innerX+nw/2,innerY+size.count.countY-nh/2,innerX+size.count.countX/2+20,innerY+size.count.countY-nh/2," ");
                    }
                    innerX+=size.count.countX/2+20+next_count.countX/2;
                    innerY+=size.count.countY-nh;
                  }
                  else if(cfg.nodes[size.next].label.includes("default")){
                    next_count=countDefault(size.next,node,countSeq,end,judge,cfg);
                    drawLineC1(innerX+nw/2,innerY+nh/2,innerX+size.count.countX/2+20+next_count.countX/2,innerY+size.count.countY-nh,"False");
                    if(size.count.innerY==0){
                      let cc=ca_count[temp]-temp;
                      drawLineC2(innerX,innerY+nh,innerX+(cc-1)*(nw+20)-nw/2,innerY+nh+cc*(nh+nm)-nh/2,"True");
                    }
                    else if(size.bre==null){
                      drawLine1(innerX+nw/2,innerY+size.count.countY-nh/2,innerX+size.count.countX/2+20,innerY+size.count.countY-nh/2," ");
                    }
                    innerX+=size.count.countX/2+20+next_count.countX/2;
                    innerY+=size.count.countY-nh;
                  }
                  else if(size.next==end[node]){
                    if(size.count.innerY==0)
                    drawLine1(innerX,innerY+nh,innerX,y+count.countY-nh-nm," ");
                    else 
                    drawLine1(innerX,innerY+size.count.countY,innerX,y+count.countY-nh-nm," ");
                    drawLineCaseEnd(innerX+nw/2,innerY+nh/2,x+nw/2,y+count.countY-nh/2,size.count.countX/2-20,"False");
                  }
                }

              
            }
            if(cfg.nodes[temp].label.includes("default")){
              size=drawDefault(temp,end[node],innerX,innerY,countSeq,end,judge,cfg);
              drawLine1(innerX,innerY+size.countY,innerX,y+count.countY-nh-nm," ");
            }
          }
          else{
            if(cfg.nodes[temp].label.includes("case")){
              size=drawCase(temp,node,innerX,innerY,countSeq,end,judge,cfg);
                  if(cfg.nodes[size.next].label.includes("case")){
                    next_count=countCase(size.next,node,countSeq,end,judge,cfg);
                    drawLineC1(innerX+nw/2,innerY+nh/2,innerX+size.count.countX/2+20+next_count.countX/2,innerY+size.count.countY-nh,"False");
                    if(size.count.innerY==0){
                      let cc=ca_count[temp]-temp;
                      drawLineC2(innerX,innerY+nh,innerX+(cc-1)*(nw+20)-nw/2,innerY+nh+cc*(nh+nm)-nh/2,"True");
                    }
                    else if(size.bre==null){
                      drawLine1(innerX+nw/2,innerY+size.count.countY-nh/2,innerX+size.count.countX/2+20,innerY+size.count.countY-nh/2," ");
                    }
                    innerX+=size.count.countX/2+20+next_count.countX/2;
                    innerY+=size.count.countY-nh;
                  }
                  else if(cfg.nodes[size.next].label.includes("default")){
                    next_count=countDefault(size.next,node,countSeq,end,judge,cfg);
                    drawLineC1(innerX+nw/2,innerY+nh/2,innerX+size.count.countX/2+20+next_count.countX/2,innerY+size.count.countY-nh,"False");
                    if(size.count.innerY==0){
                      let cc=ca_count[temp]-temp;
                      drawLineC2(innerX,innerY+nh,innerX+(cc-1)*(nw+20)-nw/2,innerY+nh+cc*(nh+nm)-nh/2,"True");
                    }
                    else if(size.bre==null){
                      drawLine1(innerX+nw/2,innerY+size.count.countY-nh/2,innerX+size.count.countX/2+20,innerY+size.count.countY-nh/2," ");
                    }
                    innerX+=size.count.countX/2+20+next_count.countX/2;
                    innerY+=size.count.countY-nh;
                  }
                  else if(size.next==end[node]){
                    drawLineCaseEnd(innerX+nw/2,innerY+nh/2,x+nw/2,y+count.countY-nh/2,size.count.countX/2-20,"False");
                  }
                }
            if(cfg.nodes[temp].label.includes("default")){
              size=drawDefault(temp,end[node],innerX,innerY,countSeq,end,judge,cfg);
              drawLineC2(innerX,innerY+size.countY,x+nw/2,y+count.countY-nm-nh/2," ");
            }
          }
      }
    }

      return count;
  }

 // drawSwitch(1,10,10,countSeq,end,judge,cfg);

  //drawSwitch(48,10,10,countSeq,end,judge,cfg);


  function drawTryOrCatOrFin(node,x,y,countSeq,end,judge,cfg){

    let count=countTryOrCatOrFin(node,countSeq,end,judge,cfg);
    let innerX=x;
    let innerY=y+nh+nm;


    let id="id: "+cfg.nodes[node].id;
    let line="line: "+cfg.nodes[node].line;
    let label="label:"+cfg.nodes[node].label;
    let defs="defs:[ "+cfg.nodes[node].defs+" ]";
    let uses="uses:[ "+cfg.nodes[node].uses+" ]";
    //绘制 try 或者 catch 或者 finally 关键字的节点框
    drawTextItem({startX:x-nw/2,startY:y,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
    start_x[node]=x-nw/2;  
    start_y[node]=y;

    //绘制向下的线段
    drawLine1(x,y+nh,x,y+nh+nm," ");
    drawLine1(x,y+count.countY-nm-nh-nm,x,y+count.countY-nm-nh," ");

    if(judge[node]==6 || judge[node]==8){
        //绘制 try 或者 finally 的结束关键字的节点框
        id="id: "+cfg.nodes[end[node]].id;
        line="line: "+cfg.nodes[end[node]].line;
        label="label:"+cfg.nodes[end[node]].label;
        defs="defs:[ "+cfg.nodes[node].defs+" ]";
        uses="uses:[ "+cfg.nodes[node].uses+" ]";
        drawTextItem({startX:x-nw/2,startY:y+count.countY-nh-nm,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
        start_x[node]=x-nw/2;  
        start_y[node]=y+count.countY-nh-nm;
      }


    for (let index = 0; index < cfg.edges.length; index++) {
      if(cfg.edges[index].source==node){
        let new_start=cfg.edges[index].target;
        
        let size;
        for (let m = 0; m < cfg.edges.length; m++) {

          if(new_start==end[node]) break;
          
              //是一个特殊的关键字
              if(judge[new_start])
              {
                if(judge[new_start]==1) size=drawIf(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==2) size=drawWhileOrFor(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==3) size=drawDo(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==4) size=drawWhileOrFor(new_start,innerX,innerY,countSeq,end,judge,cfg);
                else if(judge[new_start]==5) size=drawSwitch(new_start,innerX,innerY,countSeq,end,judge,cfg);
                innerY+=size.countY;
                for (let n = 0; n < cfg.edges.length; n++) {
                  if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                    new_start=cfg.edges[n].target;
                    break;
                  }
                }
                //break;
              }
             //是基本的顺序结构
             else if(countSeq.count[new_start]!=null){
              size=drawSeq(new_start,innerX,innerY,countSeq,end,judge,cfg);
              innerY+=size.countY;
              new_start=countSeq.next[new_start];
            }
          }
          break;
      } 
    }
    return count;
  }

  //drawTry(2,500,10,countSeq,end,judge,cfg);


  function drawTry(node,x,y,countSeq,end,judge,cfg){
      let count=countTry(node,countSeq,end,judge,cfg);

      let size=drawTryOrCatOrFin(node,x,y,countSeq,end,judge,cfg);

      let innerX=x,innerY=y+size.countY;

      //catch语句的部分
      let cat=count.cat,cat_count=count.cat_count;
      
      let cx=0,cy=0;
      for (let index = 0; index < cat_count.length; index++) {
        cx+=cat_count[index].countX+20;
        cy=Math.max(cy,cat_count[index].countY);
      }
      if(cx!=0){
        cx-=20;
        let id="id: "+cfg.nodes[end[cat[0]]].id;
        let line="line: "+cfg.nodes[end[cat[0]]].line;
        let label="label:"+cfg.nodes[end[cat[0]]].label;
        let defs="defs:[ "+cfg.nodes[node].defs+" ]";
        let uses="uses:[ "+cfg.nodes[node].uses+" ]";
        drawTextItem({startX:x-nw/2,startY:y+size.countY+cy,width:nw,height:nh,id:id,line:line,label:label,defs:defs,uses:uses});
        start_x[end[cat[0]]]=x-nw/2;  
        start_y[end[cat[0]]]=y+size.countY+cy;
      }

      let ix=innerX-cx/2,iy=innerY;
      for (let index = 0; index < cat.length; index++) {
        ix+=cat_count[index].countX/2;
        iy=innerY+(cy-cat_count[index].countY)/2;
        drawLine1(x,innerY-nm,ix,iy,"Throws");
        drawTryOrCatOrFin(cat[index],ix,iy,countSeq,end,judge,cfg);
        drawLine1(ix,iy+cat_count[index].countY-nm,x,y+size.countY+cy," ");
        ix+=cat_count[index].countX/2+20;
      }


      for (let index = 0; index < cfg.edges.length; index++) {
        if(cfg.edges[index].source==end[cat[0]]){
          if(cfg.edges[index].target==end[node])
          drawLine3(x+nw/2,innerY+cy+nh/2,x+nw/2,innerY-nm-nh/2,cx/2," ");
          else
          drawLine1(x,innerY+cy+nh,x,innerY+cy+nh+nm," ");
        }
        if(cfg.edges[index].source==end[node]){
          if(cfg.edges[index].target && cfg.edges[index].label!="Throws")
          if(cy==0)
          drawLine1(x,innerY-nm,x,innerY," ");
          else
          drawLine2(x-nw/2,innerY-nm-nh/2,x-nw/2,innerY+cy+nm+nh+nh/2,cx/2," ");
        }
      }
      return count;
  }
  
    

  function drawLineBreak(startX,startY,endX,endY,innerX){
    let ctx = getCtx();
  
    //直线连接
    ctx.beginPath();
    ctx.moveTo(startX,startY);
    ctx.lineTo(innerX,startY);
    ctx.lineTo(innerX,endY);
    ctx.lineTo(endX,endY);
    ctx.stroke();
    
    }


    function drawBreak(start_x,start_y,cfg){
      let bre_con=getBreAndCon(cfg);
      for (let index = 0; index < bre_con.bre_next.length; index++) {
        if(bre_con.bre_next[index] || bre_con.con_next[index]){
          let temp,l;
          if(bre_con.bre_next[index]){  
            l=10;
            temp=bre_con.bre_next[index];}
          else if(bre_con.con_next[index]){  
            l=15;
            temp=bre_con.con_next[index];}
          let x1=start_x[index],y1=start_y[index]+nh/2;
          let x2=start_x[temp],y2=start_y[temp]+nh/2;
          if(x1<x2){
            let inx=x1-l;
            drawLineBreak(x1,y1,x2,y2,inx);
          }
          else{
            let inx=x1+nw+l;
            drawLineBreak(x1+nw,y1,x2+nw,y2,inx);
          }
        }
      }
    }
  


    function drawFun(node,x,y,cfg){
      console.log(cfg);
      let countSeq=countSequence(cfg);
      let end=getEnd(cfg);
      let judge=getJudge(cfg);
      let count=countFun(node,countSeq,end,judge,cfg);
      let fun=getFunStartAndFunEnd(cfg);

      //let innerX=x+count.countX/2;
      let innerX=500;
      let innerY=y;

      for (let index = 0; index < cfg.edges.length; index++) {
        if(cfg.edges[index].source==node){
          let new_start=node;
          let size;
          let i=0;
          for (let m = 0; m < cfg.nodes.length; m++) {
            //if(fun.fun_end.includes(new_start)) {break;}
              //是一个特殊的关键字
            if(judge[new_start])
            {
              if(judge[new_start]==1) {
                size=drawIf(new_start,innerX,innerY,countSeq,end,judge,cfg);}
              else if(judge[new_start]==2) {
                size=drawWhileOrFor(new_start,innerX,innerY,countSeq,end,judge,cfg);}
              else if(judge[new_start]==3) {
                size=drawDo(new_start,innerX,innerY,countSeq,end,judge,cfg);}
              else if(judge[new_start]==4) {
                size=drawWhileOrFor(new_start,innerX,innerY,countSeq,end,judge,cfg);}
              else if(judge[new_start]==5) {
                size=drawSwitch(new_start,innerX,innerY,countSeq,end,judge,cfg);}
              else if(judge[new_start]==6) {
                size=drawTry(new_start,innerX,innerY,countSeq,end,judge,cfg);}
              else if(judge[new_start]==8) {
                size=drawTryOrCatOrFin(new_start,innerX,innerY,countSeq,end,judge,cfg);}
              innerY+=size.countY;
              for (let n = 0; n < cfg.edges.length; n++) {
                if(fun.fun_end.includes(end[new_start])){
                  i++;
                }
                if(cfg.edges[n].source==end[new_start] && cfg.edges[n].label!="Throws"){
                  new_start=cfg.edges[n].target;
                  break;
                }
              }
              if(i++)break;
              //break;
            }
           //是基本的顺序结构
           else if(countSeq.count[new_start]!=null){
            size=drawSeq(new_start,innerX,innerY,countSeq,end,judge,cfg);
            innerY+=size.countY;
            if(countSeq.next[new_start])
            new_start=countSeq.next[new_start];
            else
            break;
          }
          //break;
        }
      }
    }
    drawBreak(start_x,start_y,cfg);
      return count;
  }













  
  function getDDG(ddg,edges){
    let i=0;
    for (let index = 0; index < ddg.edges.length; index++) {
      if(ddg.edges[index].type=="Flows")
      edges[i++]=ddg.edges[index];
    }
    return edges;
  }

  //getDDG(ddg,edges);
  //console.log(edges);


  function getLabel(edges){
    let lab=[];
    let temp_node=edges[0].source,temp_label=edges[0].label;
    for (let index = 0; index < edges.length; index++) {
      lab[edges[index].source]=0;
    }
    for (let index = 0; index < edges.length; index++) {
      if(start_x[edges[index].source] && start_x[edges[index].target]){
        if(edges[index].source==temp_node){
          if(edges[index].label!=temp_label){
            lab[edges[index].source]++;
            temp_label=edges[index].label;
          }
        }
        else{
          temp_node=edges[index].source;
          temp_label=edges[index].label;
          lab[edges[index].source]++;
        }
      }
    }
    return lab;
  }


//console.log(getLabel(edges));

//lr奇数在左，偶数在右
  function drawCurve(startX,startY,endX,endY,label,lr){//lr指的左还是右，如果有多个参数放在两边会清晰好看一点

    let ctx = getCtx();
    ctx.strokeStyle = "red";


    let innerX=(startX+endX)/2-Math.abs(startY-endY)*0.2;
    let innerY=(startY+endY)/2;//Math.abs(startY-endY)*0.5;

    if(startX==endX){
      innerX=(startX+endX)/2-Math.abs(startY-endY)*0.2;
      innerY=(startY+endY)/2;
    }


    if(lr%2==0){
      startX+=nw;
      endX+=nw;
      innerX=(startX+endX)/2+Math.abs(startY-endY)*0.2;
    }

    //曲线连接
    ctx.beginPath();
    ctx.moveTo(startX,startY);
    ctx.quadraticCurveTo(innerX,innerY,endX,endY);
    ctx.stroke();

    ctx.fillStyle = "red";
    ctx.textBaseline = "middle";
    let { width:textWidth } = ctx.measureText(label);
    ctx.fillText(label, innerX,innerY, textWidth);
  }

  let s=6,e=30;
  //drawCurve(start_x[s],start_y[s],start_x[e],start_y[e],edges[1].label);


  function drawDDG(ddg){

    let cfg=getCfg(ddg);

    drawFun(0,0,10,cfg);

    let edges=[];

    edges=getDDG(ddg,edges);

    let lab=getLabel(edges);
    let temp_label;
    let lr=0;

    for (let index = 0; index < edges.length; index++) {
      if(start_x[edges[index].source] && start_x[edges[index].target]){
        
        let s=edges[index].source;//start
        let e=edges[index].target;//end

        if(temp_label!=edges[index].label){
        temp_label=edges[index].label;
        lr++;console.log(s,lr);
        }
        drawCurve(start_x[s],start_y[s],start_x[e],start_y[e],edges[index].label,lr);
      }
    }
  }

  //drawDDG(edges);


  export{
    drawDDG,
  }