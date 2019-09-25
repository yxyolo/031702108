import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class sudoku {
    public static int count = 0;
    private static String inputFileName;//输入的文件名
    private static String outputFileName;//输出的文件名
    private static int phraseWordNum;//阶数
    private static int sortedPrintNum;//盘数

    public static void print(int a[][])
    {
        count++;
        File file = new File(outputFileName);
        try{
            if(! file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file,true);

            for(int i = 0 ;i < phraseWordNum ;i ++){
                    for(int j = 0 ;j < phraseWordNum; j++){
                        fw.write(a[i][j]+" ");
                    }
                    fw.write("\r\n");
            }

            fw.write("\r\n");
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean test(int a[][],int i,int j,int k)//panduan k
    {
        int m;
        int n;
        int xm;
        int xn;
        for(n=0;n<phraseWordNum;n++)                                  //行遍历是否有相同数字
        if(a[i][n]==k) return false;

        for(m=0;m<phraseWordNum;m++)                                  //列遍历是否有相同数字
        if(a[m][j]==k) return false;

        if (phraseWordNum==4){
            xm=(i/2)*2;
            xn=(j/2)*2;
            for(m=xm;m<xm+2;m++)
                for(n=xn;n<xn+2;n++)
                    if(a[m][n]==k) return false;
        }
        if (phraseWordNum==6) {
            xm=(i/2)*2;
            xn=(j/3)*3;
            for(m=xm;m<xm+2;m++)
                for(n=xn;n<xn+3;n++)
                    if(a[m][n]==k) return false;
        }
        if (phraseWordNum==8) {
            xm=(i/4)*4;
            xn=(j/2)*2;
            for(m=xm;m<xm+4;m++)
                for(n=xn;n<xn+2;n++)
                    if(a[m][n]==k) return false;
        }
        if (phraseWordNum==9)
        {
            xm=(i/3)*3;
            xn=(j/3)*3;
            for(m=xm;m<xm+3;m++)
                for(n=xn;n<xn+3;n++)
                    if(a[m][n]==k) return false;
        }
        return true;
    }

    public static void SD(int a[][],int n)//求解
    {
        int i;
        int j;
        int b[][] = new int [9][9];
        for(i=0;i<phraseWordNum;i++)
        for(j=0;j<phraseWordNum;j++)
             b[i][j]=a[i][j];  //用b进行尝试
        i=n/phraseWordNum;
        j=n%phraseWordNum;
        if(a[i][j]!=0)   //如果该位置有数字
        {
            if (n == (phraseWordNum*phraseWordNum-1)) {
                print(b);
            } else {
                SD(b, n + 1);
            }
        } else{
            int k;  //试数
            for(k=1;k<=phraseWordNum;k++){
                if(test(b,i,j,k)){   //可以
                    b[i][j]=k;
                    if (n == (phraseWordNum*phraseWordNum-1)) {
                        print(b);
                    } else {
                        SD(b, n + 1);
                    }
                    b[i][j]=0;    //回溯
                }
            }
        }

    }
    public static void main(String[] args) {                                //输入
        loadargs(args);
        File file = new File(inputFileName);
        int x[][] = new int[9][9];
        int row = 0;
        int line = 0;
        String str = "";
        if(! file.exists()){
            System.out.println("对不起，不包含指定路径的文件");
        }else{
            try{
                FileReader fr = new FileReader(file);
                char[] data = new char[23];
                int length = 0;
                while((length = fr.read(data)) >0){
                    str = new String(data,0,length);
                    for(int j = 0; j < str.length(); j++){
                        if(str.charAt(j) >47 && str.charAt(j)<58){
                            char c = str.charAt(j);
                            x[row][line++] = Character.getNumericValue(c);
                            if(line == phraseWordNum){
                                //Line feed
                                row++;
                                line = 0;
                            }
                        }
                        if(row == phraseWordNum){
                            //A disk is well stored
                            SD(x,0);  //调用求解函数
                            if(count==0){
                                System.out.printf("wujie!!!!");
                                System.out.print("/n");
                            }
                            count = 0;
                            row = 0;
                            line = 0;
                        }
                    }
                }
                //Close the read stream
                fr.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void loadargs(String[] args){
        if(args.length > 0 && args != null){
            for(int i = 0; i < args.length; i++){
                switch (args[i]){
                    case "-i":
                        inputFileName = args[++i];
                        break;
                    case "-o":
                        outputFileName = args[++i];
                        break;
                    case "-m":
                        phraseWordNum = Integer.valueOf(args[++i]);
                        break;
                    case "-n":
                        sortedPrintNum = Integer.valueOf(args[++i]);
                        break;
                    default:
                        break;
                }
            }
        }else{
            System.out.println("No input parameters");
            System.exit(1);
        }
    }
}
