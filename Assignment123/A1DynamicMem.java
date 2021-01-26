// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).
    // While inserting into the list, only call insert at the head of the list
    // Please note that ALL insertions in the DLL (used either in A1DynamicMem or used independently as the dictionary class implementation) are to be made at the HEAD (from the front).
    // Also, the find-first should start searching from the head (irrespective of the use for A1DynamicMem). Similar arguments will follow with regards to the ROOT in the case of trees (specifying this in case it was not so trivial to anyone of you earlier)
    public int Allocate(int blockSize) {
        if(blockSize==0)return -1;
        Dictionary temp=freeBlk.Find(blockSize,false);
        if(temp==null)return -1;
        if(temp.key==blockSize){
            allocBlk.Insert(temp.address,temp.size,temp.key);
            //System.out.println(allocBlk.sanity()); //
            freeBlk.Delete(temp);
            //System.out.println(freeBlk.sanity()); //
            return temp.address;
        }
        if(temp.key>blockSize){
            int add1=temp.address;
            int add2=add1+blockSize;
            int key1=blockSize;
            int key2=temp.size-blockSize;
            freeBlk.Delete(temp);
            //System.out.println(freeBlk.sanity()); //
            freeBlk.Insert(add2,key2,key2);
            //System.out.println(freeBlk.sanity()); //
            allocBlk.Insert(add1,key1,key1);
            //System.out.println(allocBlk.sanity()); //
            return add1;
        }
        return -1;
    } 
    // return 0 if successful, -1 otherwise
    public int Free(int startAddr) {
        Dictionary temp=allocBlk.getFirst();
        while(temp!=null){
            if(temp.address==startAddr)break;
            temp=temp.getNext();
        }
        if(temp==null)return -1;
        freeBlk.Insert(temp.address,temp.size,temp.key);
        //System.out.println(freeBlk.sanity()); //
        allocBlk.Delete(temp);
        //System.out.println(allocBlk.sanity()); //
        return 0;
    }
}