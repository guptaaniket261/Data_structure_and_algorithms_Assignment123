// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        A1List temporary=new A1List(address,size,key);
        if(this.next==null){
            this.next=temporary;
            temporary.prev=this;
            return temporary;
        }
        temporary.next=this.next;
        this.next.prev=temporary;
        this.next=temporary;
        temporary.prev=this;
        return temporary;
    }

    public boolean Delete(Dictionary d) 
    {
        if(d==null)return false;
        A1List curr=this.getFirst();
        while(curr!=null){
            if(curr.address==d.address && curr.size==d.size && curr.key==d.key){
                if(curr.next==null || curr.prev==null)return false;  //no sentinel so it should give error;
                curr.prev.next=curr.next;
                curr.next.prev=curr.prev;
                return true;
            }
            curr=curr.getNext();
        }
        return false;
    }

    public A1List Find(int k, boolean exact)
    { 
        A1List temp=this.getFirst();
        while(temp!=null){
            if(exact==true && temp.key==k)return temp;
            if(exact==false && temp.key>=k)return temp;
            temp=temp.getNext();
        }
        return null;
    }

    public A1List getFirst()
    {
        A1List temp=this;
        if(this.prev==null){
            if(this.next==null)return null; //error only one sentinel
            else if(this.next.next==null)return null; //no  element
            else return this.next;
        }
        else if(this.next==null){
            if(this.prev==null)return null; //error only one sentinel
            else if(this.prev.prev==null)return null; // no element
            else{
                while(temp.prev.prev!=null){
                    temp=temp.prev;
                    if(temp.prev==null)return null; //error
                }
                return temp;
            }
        }
        else{
            while(temp.prev.prev!=null){
                temp=temp.prev;
                if(temp.prev==null)return null; //error
            }
            return temp;
        }
    }
    
    public A1List getNext() 
    {
        if(this.next==null)return null; //fuction called from tail sentinel
        else if(this.next.next==null)return null; //function called from element just before the tail sentinel
        else return this.next;
    }

    public boolean sanity()
    {
        //checking cycle
        A1List t1=this.getFirst(); //slow_pointer
        A1List t2=null; //fast_pointer
        if(t1!=null)t1=t1.getNext();
        if(t1!=null)t2=t1.getNext();
        while(t1!=null && t2!=null){
            if(t1==t2){return false;}
            t1=t1.getNext();
            if(t2.getNext()!=null)t2=t2.getNext().getNext();
            else break;
        }

        //checking if prev of head is null
        A1List temp=this.getFirst();
        if(temp!=null && temp.prev==null)return false;
        if(temp!=null && temp.prev.prev!=null)return false;

        
        //checking if next of tail is null

        A1List temp1=this.getFirst();
        if(temp1!=null){
            while(temp1!=null && !(temp1.key==-1 && temp1.address==-1 && temp1.size==-1)){
                temp1=temp1.next;
            }
            if(temp1==null){return false;}
            if(temp1.next!=null){return false;}
        }

        //checking node.prev.next==node for all nodes
        A1List curr=this.getFirst();
        while(curr!=null){
            if(curr.prev==null || curr.next==null)return false;
            if(curr.prev.next!=curr)return false;
            if(curr.next.prev!=curr)return false;
            curr=curr.getNext();
        }

        return true;
    }

}


