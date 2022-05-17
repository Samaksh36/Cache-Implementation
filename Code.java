package CO_EndSem;
import java.util.Scanner;
//**CODE BY SAMAKSH GUPTA**//
//**2019200**//
public class FINALASSIGNMENT {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("**THIS IS A 32 BIT MACHINE** \n");

        System.out.println("INSERT CACHE SIZE: ");
        int S = in.nextInt();

        System.out.println("INSERT NUMBER OF CACHE LINES: ");
        int CL = in.nextInt();

        System.out.println("INSERT BLOCK SIZE: ");
        int B = in.nextInt();


        System.out.println("Please enter type of mapping" +": "+ "direct , associative, set-associative" );
        String typeOfMapping=  in.next();

        if(typeOfMapping.equals("associative")) {
            Node[][]cacheArray= new Node[CL][B];

            associativeMapping dir = new associativeMapping(S, CL, B, cacheArray);
            dir.initaliseArray();
            while (true) {
                System.out.println("-------------------------------------------------------");
                System.out.println("Enter Next Command");

                String s = in.next();
                dir.physicalAddressInBinary="";
                switch (s) {
                    case "read": {
                        int address = in.nextInt();
                        dir.convertToBinary(address);
                        dir.displayInCache();
                        break;
                    }
                    case "write": {
                        int address = in.nextInt();
                        int value = in.nextInt();
                        dir.convertToBinary(address);
                        dir.writeInCacheForAssociative(value);
                        break;
                    }
                    case "print-cache":
                        dir.printTheWholeCache();
                        break;
                    case "end":
                        return;
                    default:
                        System.out.println("UNRECOGNIZABLE INPUT FORMAT");
                        break;
                }
            }
        }
        else if(typeOfMapping.equals("set-associative")) {
            System.out.println("enter n: ");
            int n = in.nextInt();

            if (n == 0) {
                Node[][]cacheArray= new Node[CL][B];

                associativeMapping dir = new associativeMapping(S, CL, B, cacheArray);
                dir.initaliseArray();
                System.out.println("**ENTERED FULLY ASSOCIATIVE MAPPING**");
                while (true) {
                    System.out.println("-------------------------------------------------------");
                    System.out.println("Enter Next Command");

                    String s = in.next();
                    dir.physicalAddressInBinary="";
                    switch (s) {
                        case "read": {
                            int address = in.nextInt();
                            dir.convertToBinary(address);
                            dir.displayInCache();
                            break;
                        }
                        case "write": {
                            int address = in.nextInt();
                            int value = in.nextInt();
                            dir.convertToBinary(address);
                            dir.writeInCacheForAssociative(value);
                            break;
                        }
                        case "print-cache":
                            dir.printTheWholeCache();
                            break;
                        case "end":
                            return;
                        default:
                            System.out.println("UNRECOGNIZABLE INPUT FORMAT");
                            break;
                    }
                }

            }



            else {
                Node[][][] cacheArray = new Node[1][1][1];
                System.out.println("**ENTERED "+n+"-WAY SET ASSOCIATIVE MAPPING **");
                setAssociativeMapping dir = new setAssociativeMapping(S, CL, B, n, cacheArray);
                dir.initialiseArray();
                while (true) {
                    System.out.println("-------------------------------------------------------");
                    System.out.println("Enter Next Command");
                    String s = in.next();
                    dir.physicalAddressInBinary = "";
                    switch (s) {
                        case "read": {
                            int address = in.nextInt();
                            dir.convertToBinary(address);
                            dir.displayInCacheFOrSet();
                            break;
                        }
                        case "write": {
                            int address = in.nextInt();
                            int value = in.nextInt();
                            dir.convertToBinary(address);
                            dir.writeInCacheForSet(value);
                            break;
                        }
                        case "print-cache":
                            dir.printTheWholeCache();
                            break;
                        case "end":
                            return;
                        default:
                            System.out.println("UNRECOGNIZABLE INPUT FORMAT");
                            break;
                    }
                }
            }
        }
        else{
            Node[][]cacheArray= new Node[1][1];
            System.out.println("**ENTERED DIRECT MAPPING**");
            directMapping dir= new directMapping(S, CL, B, cacheArray );
            dir.initialiseArray();
            while (true) {
                System.out.println("-------------------------------------------------------");
                System.out.println("Enter Next Command");
                String s = in.next();
                dir.physicalAddressInBinary="";
                switch (s) {
                    case "read": {
                        int address = in.nextInt();
                        dir.convertToBinary(address);
                        dir.displayInCache();
                        break;
                    }
                    case "write": {
                        int address = in.nextInt();
                        int value = in.nextInt();
                        dir.convertToBinary(address);
                        dir.writeInCacheForDirect(value);
                        break;
                    }
                    case "print-cache":
                        dir.printTheWholeCache();
                        break;
                    case "end":
                        return;
                    default:
                        System.out.println("UNRECOGNIZABLE INPUT FORMAT");
                        break;
                }
            }
        }
        }
    }


    class directMapping {
        int S;
        int CL;
        int B;
        String physicalAddressInBinary = "";
        Node[][] cacheArray;


        directMapping(int S, int CL, int B, Node[][] cacheArray) {
            this.S = S;
            this.CL = CL;
            this.B = B;
            this.cacheArray = new Node[CL][B];
        }

        public void initialiseArray() {
            for (int i = 0; i < CL; i++) {
                for (int j = 0; j < B; j++) {
                    Node temp = new Node(0);
                    cacheArray[i][j] = temp;
                }
            }
        }


        public String convertToBinary(int loc) {
            int a;
            while (loc > 0) {
                a = loc % 2;
                physicalAddressInBinary = a + physicalAddressInBinary;
                loc = loc / 2;
            }
            while (physicalAddressInBinary.length() != 32) {
                physicalAddressInBinary = "0" + physicalAddressInBinary;
            }
            return (physicalAddressInBinary);
        }

        public int convertToDecimal(String s) {
            return (Integer.parseInt(s, 2));
        }


        private int blockOffsetBits() {
            double blockOffset = Math.log10(B) / Math.log10(2);
            return ((int) blockOffset);
        }

        private int generateBlockOffsetAddress() {
            int bitsrequired = blockOffsetBits();
            String x = physicalAddressInBinary;
            StringBuilder BlockAddress = new StringBuilder();
            int i = 32 - bitsrequired;
            while (bitsrequired > 0) {
                BlockAddress.append(x.charAt(i));
                bitsrequired--;
                i++;
            }

            return convertToDecimal(BlockAddress.toString());

        }


        private int lineIndexBits() {
            double LineBits = Math.log10(CL) / Math.log10(2);
            return ((int) LineBits);
        }

        private int generateLineAddress() {
            int bitsTobeLeft = blockOffsetBits();

            int bitsrequired = lineIndexBits();

            String x = physicalAddressInBinary;
            StringBuilder lineAddress = new StringBuilder();

            int i = 32 - bitsTobeLeft - bitsrequired;

            while (bitsrequired > 0) {
                lineAddress.append(x.charAt(i));
                bitsrequired--;
                i++;
            }
            return convertToDecimal(lineAddress.toString());
        }


        private int generateTagAddress() {
            int j = 32 - blockOffsetBits() - lineIndexBits();
            StringBuilder tag = new StringBuilder();
            String x = physicalAddressInBinary;
            int i = 0;
            while (j > 0) {
                tag.append(x.charAt(i));
                j--;
                i++;
            }

            return convertToDecimal(tag.toString());
        }

        private int generateBlockNumberAddress() {
            int bitsrequired = 32 - blockOffsetBits();
            StringBuilder blockNumber = new StringBuilder();
            String x = physicalAddressInBinary;
            int i = 0;
            while (bitsrequired > 0) {
                blockNumber.append(x.charAt(i));
                bitsrequired--;
                i++;
            }

            return convertToDecimal(blockNumber.toString());
        }


        public void writeInCacheForDirect(int value) {
            Node val = new Node(value);
            String x = physicalAddressInBinary;
            val.address = convertToDecimal(x);
            int tagValue = generateTagAddress();
            val.tag = tagValue;
            System.out.println(tagValue + "   :TAG");

            for (int i = 0; i < B; i++) {
                if (cacheArray[generateLineAddress()][i].tag == val.tag) {

                    System.out.println("TAG FOUND");
                    if (cacheArray[generateLineAddress()][generateBlockOffsetAddress()].data != 0) {
                        System.out.println("REPLACING: " + cacheArray[generateLineAddress()][generateBlockOffsetAddress()].data + " \t AT ADDRESS: " + convertToDecimal(x) + "   \tWith New Value Provided");

                        cacheArray[generateLineAddress()][generateBlockOffsetAddress()] = val;
                        System.out.println("VALUE: " + value + " \t\t AT ADDRESS: " + convertToDecimal(x) + " \t Inserted");
                        return;
                    } else {
                        System.out.println("VALUE: " + value + " \t\t AT ADDRESS: " + convertToDecimal(x) + " \t Inserted");
                        cacheArray[generateLineAddress()][generateBlockOffsetAddress()] = val;
                        return;
                    }
                }
            }
//If tag wasn't found and the mapped line isn't empty, then empty the mapped line...
            System.out.println("DATA FROM NEW BLOCK HAS TO BE WRITTEN");
            System.out.println("PREPARING THE MAPPED LINE: " + generateLineAddress());

            for (int i = 0; i < B; i++) {
                if (cacheArray[generateLineAddress()][i].data != 0) {
                    System.out.println("VALUE: " + cacheArray[generateLineAddress()][i].data + " \t\t  DELETED FROM ADDRESS: " + cacheArray[generateLineAddress()][i].address);
                }
                Node temp = new Node(0);
                temp.tag = tagValue;
                cacheArray[generateLineAddress()][i] = temp;
            }
            cacheArray[generateLineAddress()][generateBlockOffsetAddress()] = val;

            System.out.println("VALUE: " + value + " \t\t AT ADDRESS: " + convertToDecimal(x) + " \t INSERTED");

        }


        public void displayInCache() {
            int lineNumber = generateLineAddress();
            int blockOffSet = generateBlockOffsetAddress();
            String x = physicalAddressInBinary;

            for (int i = 0; i < B; i++) {
                if (generateTagAddress() == cacheArray[lineNumber][i].tag) {
                    if (cacheArray[lineNumber][blockOffSet].data != 0) {
                        System.out.println("**CACHE HIT**");
                        System.out.println("ADDRESS: " + convertToDecimal(x));
                        System.out.println("VALUE: " + cacheArray[lineNumber][blockOffSet].data);
                        return;

                    } else {
                        System.out.println("ADDRESS EXISTS IN CACHE HOWEVER NOTHING HAS BEEN WRITTEN ON IT\nADDRESS IS IN LINE: " + lineNumber);
                        return;
                    }
                }
            }
            System.out.println("**CACHE MISS**");
            System.out.println("ADDRESS NOT FOUND: " + convertToDecimal(x));
        }

        public void printTheWholeCache() {
            System.out.println("*************************************************************************************************");
            System.out.println("*************************************************************************************************");
            for (int i = 0; i < CL; i++) {
                for (int j = 0; j < B; j++) {

                    if (cacheArray[i][j].tag != -1 && cacheArray[i][j].data == 0) {
                     String a1= binaryMaker(cacheArray[i][j].tag, (32- blockOffsetBits()- lineIndexBits()));
                        String a2= binaryMaker(i, (lineIndexBits()));
                        String a3= binaryMaker(j, (blockOffsetBits()));
                        String a= a1+a2+a3;
                        int add= convertToDecimal(a);
                        System.out.println("ADDRESS: " + add + "\t\t" + "TAG: " + cacheArray[i][j].tag + "\t\t" + "LINE NUMBER: " + i + "\t\t" + "BLOCK OFFSET: " + j + "\t\t" + "VALUE: " + cacheArray[i][j].data + "\t\tADDRESS EXISTS IN CACHE, BUT NOTHING HAS BEEN WRITTEN ON IT");
                    }
                    else if (cacheArray[i][j].tag != -1 && cacheArray[i][j].data != 0) {
                        System.out.println("ADDRESS: " + cacheArray[i][j].address + "\t\t" + "TAG: " + cacheArray[i][j].tag + "\t\t" + "LINE NUMBER: " + i + "\t\t" + "BLOCK OFFSET: " + j + "\t\t" + "VALUE: " + cacheArray[i][j].data + "\t\tADDRESS HAS BEEN WRITTEN TO");
                    } else {
                        System.out.println("ADDRESS: " + cacheArray[i][j].address + "\t\t" + "TAG: " + cacheArray[i][j].tag + "\t\t" + "LINE NUMBER: " + i + "\t\t" + "BLOCK OFFSET: " + j + "\t\t" + "VALUE: " + cacheArray[i][j].data);
                    }

                }
                System.out.println("*************************************************************************************************");
            }

            System.out.println("*************************************************************************************************");
        }

        private String binaryMaker(int num, int bits) {
            int a;
            String physicalAddressInBinaryIndi="";
            while (num > 0) {
                a = num% 2;

                physicalAddressInBinaryIndi = a + physicalAddressInBinaryIndi;
                num = num / 2;
            }
            while (physicalAddressInBinaryIndi.length() != bits) {
                physicalAddressInBinaryIndi = "0" + physicalAddressInBinaryIndi;
            }
            return (physicalAddressInBinaryIndi);
        }

    }
class associativeMapping {
    int S;
    int CL;
    int B;
    String physicalAddressInBinary = "";

    Node[][] cacheArray;


    associativeMapping(int S, int CL, int B, Node[][] cacheArray) {
        this.S = S;
        this.CL = CL;
        this.B = B;
        this.cacheArray = new Node[CL][B];
    }

    public void initaliseArray() {
        for (int i = 0; i < CL; i++) {
            for (int j = 0; j < B; j++) {
                Node temp = new Node(0);
                cacheArray[i][j] = temp;
            }
        }
    }

    public String convertToBinary(int loc) {
        int a;
        while (loc > 0) {
            a = loc % 2;

            physicalAddressInBinary = a + physicalAddressInBinary;
            loc = loc / 2;
        }
        while (physicalAddressInBinary.length() != 32) {
            physicalAddressInBinary = "0" + physicalAddressInBinary;
        }
        return (physicalAddressInBinary);
    }

    public int convertToDecimal(String s) {

        return (Integer.parseInt(s, 2));

    }


    private int blockOffsetBits() {
        double blockOffset = Math.log10(B) / Math.log10(2);
        return ((int) blockOffset);
    }

    private int generateBlockOffsetAddress() {
        int bitsrequired = blockOffsetBits();
        String x = physicalAddressInBinary;
        StringBuilder BlockAddress = new StringBuilder();
        int i = 32 - bitsrequired;
        while (bitsrequired > 0) {
            BlockAddress.append(x.charAt(i));
            bitsrequired--;
            i++;
        }

        return convertToDecimal(BlockAddress.toString());
    }

    private int generateTagAddress() {
        return generateBlockNumberAddress();
    }

    private int generateBlockNumberAddress() {
        int bitsrequired = 32 - blockOffsetBits();
        StringBuilder blockNumber = new StringBuilder();
        String x = physicalAddressInBinary;
        int i = 0;
        while (bitsrequired > 0) {
            blockNumber.append(x.charAt(i));
            bitsrequired--;
            i++;
        }

        return convertToDecimal(blockNumber.toString());
    }


    public void writeInCacheForAssociative(int value) {
        Node val = new Node(value);
        String x = physicalAddressInBinary;
        int blockOffset = generateBlockOffsetAddress();
        val.address = convertToDecimal(x);
        val.tag = generateBlockNumberAddress();

        //First find the Tag.
        for (int i = 0; i < CL; i++) {
            for (int j = 0; j < B; j++) {
                //If the tag is found, add the number to it's position in that line in which, numbers with its tag exist.
                if (cacheArray[i][j] != null) {
                    if (cacheArray[i][j].data != 0 && cacheArray[i][j].tag == val.tag) {
                        System.out.println("TAG FOUND... INSERTING IN LINE: " + i);
                        if (cacheArray[i][blockOffset] != null && cacheArray[i][blockOffset].data != 0)
                            System.out.println("REPLACING " + cacheArray[i][blockOffset].data + " With New Value Provided AT ADDRESS: " + convertToDecimal(x));
                        System.out.println("VALUE: " + value + " \t\t AT ADDRESS: " + convertToDecimal(x) + " \t INSERTED");

                        cacheArray[i][blockOffset] = val;
                        return;
                    }
                }
            }
        }

        //If tag not found in cache. Find an empty Line and add the element to it.

        for (int i = 0; i < CL; i++) {
            int lineNullCounter = 0;
            for (int j = 0; j < B; j++) {
                if (cacheArray[i][j].data == 0) {
                    lineNullCounter++;
                }
            }
            //Empty Line will have BlockSize number of nulls.
            if (lineNullCounter == B) {
                System.out.println("TAG NOT FOUND... INSERTING IN A NEW EMPTY LINE... : " + i);
                System.out.println("VALUE: " + value + " \t\t AT ADDRESS: " + convertToDecimal(x) + " \t INSERTED");

                for (int j = 0; j < B; j++) {
                    Node temp = new Node(0);
                    temp.tag = generateBlockNumberAddress();
                    cacheArray[i][j] = temp;
                }
                //Initialise the whole Line with the Address of the block.
                cacheArray[i][blockOffset] = val;
                return;
            }
        }
        //If no empty Line and No tag match then as per random replacement policy, remove a complete Line and add the new element to it.
        int max = CL - 1;
        int min = 0;
        int range = max - min + 1;
        int rand = (int) (Math.random() * range) + min;
        System.out.println("TAG NOT FOUND... NO FREE LINE IN THE CACHE... PROCEEDING TO REMOVE CONTENTS OF A RANDOM LINE\n\n \t \t \t \t REMOVING CONTENTS OF LINE: " + rand + "\n");


        for (int j = 0; j < B; j++) {
            Node temp = new Node(0);
            temp.tag = generateBlockNumberAddress();
            if (cacheArray[rand][j] != null && cacheArray[rand][j].address != -1) {
                System.out.println("ADDRESS: " + (cacheArray[rand][j].address) + "\t \tREMOVED FROM LINE: " + rand);
            }
            cacheArray[rand][j] = temp;
        }


        System.out.println("VALUE: " + value + " \t\t AT ADDRESS: " + convertToDecimal(x) + " \t INSERTED");
        cacheArray[rand][blockOffset] = val;
    }


    public void displayInCache() {
        int tag = generateBlockNumberAddress();
        int blockOffset = generateBlockOffsetAddress();
        String x = physicalAddressInBinary;
        for (int i = 0; i < CL; i++) {

            for (int j = 0; j < B; j++) {

                if (cacheArray[i][j].tag == tag) {

                    if (j == blockOffset && cacheArray[i][j].data != 0) {
                        System.out.println("**CACHE HIT**");
                        System.out.println("ADDRESS: " + convertToDecimal(x));
                        System.out.println("VALUE: " + cacheArray[i][blockOffset].data);
                        return;
                    } else if (j == blockOffset && cacheArray[i][j].data == 0) {
                        System.out.println("ADDRESS EXISTS IN CACHE HOWEVER NOTHING HAS BEEN WRITTEN ON IT\nADDRESS IS IN LINE: " + i);
                        return;
                    }
                }
            }
        }
        System.out.println("**CACHE MISS**");
        System.out.println("ADDRESS NOT FOUND: " + convertToDecimal(x));
    }


    public void printTheWholeCache() {
        System.out.println("*************************************************************************************");
        System.out.println("*************************************************************************************");

        for (int i = 0; i < CL; i++) {
            for (int j = 0; j < B; j++) {
                if (cacheArray[i][j].tag != -1 && cacheArray[i][j].data == 0) {
                    String a1 = binaryMaker(cacheArray[i][j].tag, 32 - blockOffsetBits());
                    String a2 = binaryMaker(j, blockOffsetBits());
                    String a = a1 + a2;
                    int add = convertToDecimal(a);
                    System.out.println("ADDRESS : " + add + "\t\t" + "TAG: " + cacheArray[i][j].tag + "\t\t" + "LINE NUMBER: " + i + "\t\t" + "BLOCK OFFSET: " + j + "\t\t" + "VALUE: " + cacheArray[i][j].data + "\t\t ADDRESS EXISTS IN CACHE, BUT NOTHING HAS BEEN WRITTEN ON IT");
                } else if (cacheArray[i][j].tag != -1 && cacheArray[i][j].data != 0) {
                    System.out.println("ADDRESS : " + cacheArray[i][j].address + "\t\t" + "TAG: " + cacheArray[i][j].tag + "\t\t" + "LINE NUMBER: " + i + "\t\t" + "BLOCK OFFSET: " + j + "\t\t" + "VALUE: " + cacheArray[i][j].data + "\t\t ADDRESS HAS BEEN WRITTEN TO");
                } else {
                    System.out.println("ADDRESS : " + cacheArray[i][j].address + "\t\t" + "TAG: " + cacheArray[i][j].tag + "\t\t" + "LINE NUMBER: " + i + "\t\t" + "BLOCK OFFSET: " + j + "\t\t" + "VALUE: " + cacheArray[i][j].data);
                }
            }
            System.out.println("*************************************************************************************");
        }
        System.out.println("*************************************************************************************");

    }

    private String binaryMaker(int num, int bits) {
        int a;
      
        String physicalAddressInBinaryIndi="";
        while (num > 0) {
            a = num% 2;

            physicalAddressInBinaryIndi = a + physicalAddressInBinaryIndi;
            num = num / 2;
        }
        while (physicalAddressInBinaryIndi.length() != bits) {
            physicalAddressInBinaryIndi = "0" + physicalAddressInBinaryIndi;
        }
        return (physicalAddressInBinaryIndi);
        
    }
}


class setAssociativeMapping {
    int S;
    int CL;
    int B;
    int n;
    String physicalAddressInBinary = "";

    Node[][][] cacheArray;


    setAssociativeMapping(int S, int CL, int B, int n, Node[][][] cacheArray) {
        this.S = S;
        this.CL = CL;
        this.B = B;
        this.n=n;
        this.cacheArray = new Node[CL/n][CL/(CL/n)][B];

    }
    public void initialiseArray(){
        for(int i=0; i<CL/n; i++){
            for(int j=0; j<n;j++){
                for(int k=0; k<B; k++){
                    Node temp= new Node(0);
                    cacheArray[i][j][k]=temp;
                }
            }
        }
    }


    public String convertToBinary(int loc) {
        int a;
        while (loc > 0) {
            a = loc % 2;

            physicalAddressInBinary = a + physicalAddressInBinary;
            loc = loc / 2;
        }
        while (physicalAddressInBinary.length() != 32) {
            physicalAddressInBinary = "0" + physicalAddressInBinary;
        }
        return (physicalAddressInBinary);
    }

    public int convertToDecimal(String s) {

        return (Integer.parseInt(s, 2));

    }


    private int blockOffsetBits() {
        double blockOffset = Math.log10(B) / Math.log10(2);
        return ((int) blockOffset);
    }

    private int generateBlockOffsetAddress() {
        int bitsrequired = blockOffsetBits();
        String x = physicalAddressInBinary;
        StringBuilder BlockAddress = new StringBuilder();
        int i = 32 - bitsrequired;
        while (bitsrequired > 0) {
            BlockAddress.append(x.charAt(i));
            bitsrequired--;
            i++;
        }

        return convertToDecimal(BlockAddress.toString());

    }


    private int generateTag() {
    String x= physicalAddressInBinary;
    StringBuilder y= new StringBuilder();
    int bitsToLeave= generateSetBits()+ blockOffsetBits();

    int bitsRequired= 32- bitsToLeave;
    int i=0;
    while(i<bitsRequired){
        y.append(x.charAt(i));
    i++;}

    return convertToDecimal(y.toString());

    }

    private int generateSetBits(){
        int numberOfSets= CL/n;
       double bitsRequired = ( Math.log10(numberOfSets)/Math.log10(2));
        return (int)bitsRequired;
    }
    private int generateSetAddress(){
        int bitsRequired= generateSetBits();
        String x = physicalAddressInBinary;
        int removeBits= blockOffsetBits()+generateSetBits();
        StringBuilder y= new StringBuilder();
        int i= 32-removeBits;

        while(bitsRequired>0){
            y.append(x.charAt(i));
            i++;
            bitsRequired--;
        }

        return convertToDecimal(y.toString());
    }

    private int generateBlockNumberAddress() {
        int bitsrequired = 32 - blockOffsetBits();
        StringBuilder blockNumber = new StringBuilder();
        String x = physicalAddressInBinary;
        int i = 0;
        while (bitsrequired > 0) {
            blockNumber.append(x.charAt(i));
            bitsrequired--;
            i++;
        }

        return convertToDecimal(blockNumber.toString());
    }

    private int generateNumberOfSets(){
        return (CL/n);
    }

    public void writeInCacheForSet(int value){
        Node val= new Node(value);
        String x= physicalAddressInBinary;
        val.address= convertToDecimal(x);
        int tag= generateTag();
        val.tag= tag;


        int blockNumber= generateBlockNumberAddress();
        int blockOffset= generateBlockOffsetAddress();
        int setNumber= generateSetAddress();


        int max = (CL/generateNumberOfSets()) - 1;
        int min = 0;
        int range = max - min + 1;
        int rand = (int) (Math.random() * range) + min;
        //Find tag in each line of the concerned set. If tag found, insert in BlockOffSet of the line in which tag was found.

            for(int j=0; j<(CL/generateNumberOfSets()); j++){
                for(int k=0; k<B; k++){
                    if(cacheArray[setNumber][j][k].tag== val.tag){
                        System.out.println("TAG FOUND... INSERTING IN LINE: "+ j +" \t\t OF SET: "+ setNumber);
                        if(cacheArray[setNumber][j][blockOffset].data!= 0) System.out.println("REPLACING "+ cacheArray[setNumber][j][blockOffset].data+"    AT ADDRESS: "+ convertToDecimal(x) +"   \tWith New Value Provided");
                        System.out.println("VALUE: " + value+ " \t\t AT ADDRESS: "+ convertToDecimal(x)+ " \t INSERTED");
                        cacheArray[setNumber][j][blockOffset]= val;
                        return;
                    }
                }

        }
            //If tag not found then look for an empty line in the concerned set.

            for(int i=0; i<CL/generateNumberOfSets(); i++){
                int emptyPoint=0;
                for(int j=0; j<B; j++){
                    if(cacheArray[setNumber][i][j].data==0)emptyPoint++;
                }

                if(emptyPoint==B){
                    System.out.println("TAG NOT FOUND... INSERTING IN A NEW EMPTY LINE... : "+ i +"    OF SET: "+ setNumber);
                    System.out.println("VALUE: " + value+ " \t\t AT ADDRESS: "+ convertToDecimal(x)+ " \t INSERTED");

                    for(int j=0; j<B; j++){Node temp= new Node(0);
                    temp.tag=tag;
                    cacheArray[setNumber][i][j]=temp;}
                    cacheArray[setNumber][i][blockOffset]= val;
                    return;
                }
            }
            //If tag not found and no empty line, Then make a random line empty in the concerned set.

        System.out.println("TAG NOT FOUND... NO FREE LINE IN THE SET... PROCEEDING TO DELETE A RANDOM LINE\n \t \t \t \t DELETE FROM LINE: "+ rand+ "      \t OF SET: "+ setNumber);

        for (int j = 0; j < B; j++) {
            if(cacheArray[setNumber][rand][j].data!=0) System.out.println("ADDRESS: "+(cacheArray[setNumber][rand][j].address) + "      DELETED FROM LINE: "+ rand+ "        OF SET: "+ setNumber);
            Node temp= new Node(0);
            temp.tag=tag;
            cacheArray[setNumber][rand][j]=temp;
        }
        System.out.println("VALUE: " + value+ " \t\t AT ADDRESS: "+ convertToDecimal(x)+ " \t INSERTED");
        cacheArray[setNumber][rand][blockOffset]= val;
    }



    public void displayInCacheFOrSet(){
        int blockNumber= generateBlockNumberAddress();
        int blockOffset= generateBlockOffsetAddress();

        int setNumber= generateSetAddress();
        int tag= generateTag();
        int max= (CL/generateNumberOfSets())-1;
        String x= physicalAddressInBinary;


        for(int i=0; i<(CL/generateNumberOfSets()); i++){
            for(int j=0; j<B; j++){
                if(cacheArray[setNumber][i][j].tag== tag){
                    if(j==blockOffset && cacheArray[setNumber][i][j].data!=0){
                        System.out.println("**CACHE HIT**");
                        System.out.println("VALUE: "+ cacheArray[setNumber][i][blockOffset].data+ "  AT ADDRESS: "+ convertToDecimal(x));
                        return;
                    }
                    else if(j==blockOffset && cacheArray[setNumber][i][j].data==0){
                    System.out.println("ADDRESS EXISTS IN CACHE HOWEVER NOTHING HAS BEEN WRITTEN ON IT\nADDRESS IS IN LINE: " + i +"\t\t\t OF SET: "+ setNumber);return;
                }
                }
            }
        }
        System.out.println("**CACHE MISS**");
        System.out.println("ADDRESS NOT FOUND: " + convertToDecimal(x));
        }

        private void getBinary(int num, int bits){

        }

        public void printTheWholeCache(){
            System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////////////////");
            System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////////////////");
            System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////////////////");

        for(int i=0; i<generateNumberOfSets(); i++){
            for(int j=0; j<(CL/generateNumberOfSets()); j++){
                for(int k=0; k<B; k++){
                    if (cacheArray[i][j][k].tag != -1 && cacheArray[i][j][k].data == 0) {
                        String a1 = binaryMaker(cacheArray[i][j][k].tag, 32 - blockOffsetBits()-generateSetBits());
                        String a2 = binaryMaker(i, generateSetBits());
                        String a3=binaryMaker(k, blockOffsetBits());
                        String a = a1 + a2+ a3;
                        int add = convertToDecimal(a);
                    System.out.println("ADDRESS : " +add+"\t\t"+"TAG: "+cacheArray[i][j][k].tag+"\t\t"+"SET: "+i+"\t\t"+"LINE NUMBER: "+j+ "\t\t"+"BLOCK OFFSET: "+k+ "\t\t"+ "VALUE: " + cacheArray[i][j][k].data+"\t\tADDRESS EXISTS IN CACHE, BUT NOTHING HAS BEEN WRITTEN ON IT");
                    }

                    else if(cacheArray[i][j][k].tag != -1 && cacheArray[i][j][k].data != 0){
                        System.out.println("ADDRESS : " +cacheArray[i][j][k].address+"\t\t"+"TAG: "+cacheArray[i][j][k].tag+"\t\t"+"SET: "+i+"\t\t"+"LINE NUMBER: "+j+ "\t\t"+"BLOCK OFFSET: "+k+ "\t\t"+ "VALUE: " + cacheArray[i][j][k].data+"\t\tADDRESS HAS BEEN WRITTEN TO");
                    }
                    else{
                        System.out.println("ADDRESS : " +cacheArray[i][j][k].address+"\t\t"+"TAG: "+cacheArray[i][j][k].tag+"\t\t"+"SET: "+i+"\t\t"+"LINE NUMBER: "+j+ "\t\t"+"BLOCK OFFSET: "+k+ "\t\t"+ "VALUE: " + cacheArray[i][j][k].data);

                    }
                }


                System.out.println("**************************************************************************************************************");
            }
            System.out.println("**************************************************************************************************************");
            System.out.println("**************************************************************************************************************");
        }
            System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////////////////");
        }

    private String binaryMaker(int num, int bits) {
        int a;

        String physicalAddressInBinaryIndi="";
        while (num > 0) {
            a = num% 2;

            physicalAddressInBinaryIndi = a + physicalAddressInBinaryIndi;
            num = num / 2;
        }
        while (physicalAddressInBinaryIndi.length() != bits) {
            physicalAddressInBinaryIndi = "0" + physicalAddressInBinaryIndi;
        }
        return (physicalAddressInBinaryIndi);

    }

}



class Node {
    int data;
    long address=-1;
    int tag=-1;
    Node(int data) {
        this.data = data;
    }
}