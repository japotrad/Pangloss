var  secOutMgr = {
 fPathSecOutCo : "ide:tplTdCo/des:div.secOutFra/chi:div.secOutUi/chi:ul",
 fPathSecOutBtn : "ide:tplTdCo/des:div.secOutFra/chi:div.secOutTi/chi:a",
 fCo : null,
 fBtn : null,
 fClassOp : "secOut_op",
 fClassCl : "secOut_cl",
 onLoad : function() {
  this.fCo = scPaLib.findNode(this.fPathSecOutCo);
  this.fBtn = scPaLib.findNode(this.fPathSecOutBtn);
 },
 toggle : function() {
   scDynUiMgr.collBlkToggle(this.fBtn,this.fCo,this.fClassOp,this.fClassCl);
 },
 loadSortKey : "AC"
}
scOnLoads[scOnLoads.length] = secOutMgr;
