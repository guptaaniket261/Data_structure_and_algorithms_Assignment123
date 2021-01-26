// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // I am overloading the functions of A1DynamicMem since I missed a few cases in A1DynamicMem (but not sure if those cases are of significance)
    // 

    public int Allocate(int blockSize) {
        
        if(blockSize<=0)return -1;
        Dictionary temp=freeBlk.Find(blockSize,false);
        if(temp==null){return -1;}
        else if(temp.key==blockSize){
            allocBlk.Insert(temp.address,temp.size,temp.address);
            //System.out.println(freeBlk.sanity());  //
            freeBlk.Delete(temp);
            //System.out.println(freeBlk.sanity());  //
            return temp.address;
        }
        else{
            freeBlk.Delete(temp);
            //System.out.println(freeBlk.sanity());   // 
            freeBlk.Insert(temp.address+blockSize,temp.size-blockSize,temp.size-blockSize);
            //System.out.println(freeBlk.sanity());   //
            allocBlk.Insert(temp.address,blockSize,temp.address);
            //System.out.println(freeBlk.sanity());   //
            return temp.address;
        }
    } 

    // return 0 if successful, -1 otherwise

    public int Free(int startAddr) {
        Dictionary temp=allocBlk.Find(startAddr,true);
        if(temp==null)return -1;
        freeBlk.Insert(temp.address,temp.size,temp.size);
        //System.out.println(freeBlk.sanity()); //
        
        allocBlk.Delete(temp);
        //System.out.println(allocBlk.sanity()); //

        return 0;
    }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public void Defragment() {
        Dictionary temp;    //temp is the sentinel root for the temporary tree built during defragmentation
        if(this.type==1)return;
        if(this.type==2)temp=new BSTree();
        else temp=new AVLTree();

        Dictionary cur=this.freeBlk.getFirst();         
        while(cur!=null){                  //inorder traversal of freeBlk to insert the memory blocks in the temporary tree
            temp.Insert(cur.address,cur.size,cur.address);
            cur=cur.getNext();
        }

        Dictionary F=temp.getFirst();
        if(F==null)return;
        Dictionary S=F.getNext();
        while(S!=null){
            if(F.address+F.size==S.address){
                int addr=F.address;
                int siz=F.size+S.size;
                int ke=F.key;
                Dictionary t1,t2;
                if(this.type==2){
                    t1=new BSTree(F.address,F.size,F.size);
                    t2=new BSTree(S.address,S.size,S.size);
                }
                else{
                    t1=new AVLTree(F.address,F.size,F.size);
                    t2=new AVLTree(S.address,S.size,S.size);
                }
                
                temp.Delete(F);
                temp.Delete(S);
                //F.key=F.size;
                //S.key=S.size;
                freeBlk.Delete(t1);
                freeBlk.Delete(t2);
                freeBlk.Insert(addr,siz,siz);
                F=temp.Insert(addr,siz,ke);
                S=F.getNext();
            }
            else{
                F=S;
                S=F.getNext();
            }
        }
        temp=null; 
        //System.out.println(freeBlk.sanity());   //
        return;
    }

    
}