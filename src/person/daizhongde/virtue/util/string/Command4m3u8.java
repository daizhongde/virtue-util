package person.daizhongde.virtue.util.string;


public class Command4m3u8 {
	/**
	 * 
	 * @param args
	 */
	  public static void main(String[] args)  {

//		  copy 0.ts/b + 1.ts/b  2-nnpj180-5b.ts   2-ssni076-5b.mp4
		  int start=0;
		  int end = 320;//700 701 951
		  String fileName = "a000000.ts";
		  
		  String copy = "copy "+start+".ts/b ";
		  
		  for(int i=start+1; i<=end ;i++){
			  copy +="+ "+i+".ts/b ";
		  }
		  copy += fileName;
		  System.out.println("#####    copy command  ##### \n"+copy);
	  }
}
/*
.142ec324148c3c8732bfe56e80ba2bfc  2-nnpj180-5b.mp4  524
.914037cdf9180b09658ba6900421a6d5  2-xvsr154-5b.mp4  492

.3365788dd0439f005af11ac5777b9b3e  2-mxgs896-5b.mp4  536
.64838737b0708c36f9d741842db788bc  2-nnpj185-5b.mp4  767
.b035ecdd65ae1b46c687aaf85f95ba1c  2-shkd734-5b.mp4  403


 */