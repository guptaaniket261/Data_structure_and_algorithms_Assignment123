// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    private BSTree getRoot(){
        BSTree temp=this;
        while(temp.parent!=null){
            temp=temp.parent;
        }
        return temp;
    }

    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree temp=new BSTree(address,size,key);
        BSTree curr=this.getRoot();  //the head sentinel node
        if(curr.right==null){
            //when tree is empty
            curr.right=temp;
            temp.parent=curr;
            return temp;
        }
        if(curr.parent==null)curr=curr.right;
        BSTree par=curr.parent;
        while(curr!=null){
            if(key<curr.key || (key==curr.key && address<=curr.address)){
                par=curr;
                curr=curr.left;
            }
            else{
                par=curr;
                curr=curr.right;
            }
        }
        if(key<par.key || (key==par.key && address<=par.address)){
            par.left=temp;
            temp.parent=par;
        } 
        else{
            par.right=temp;
            temp.parent=par;
        }
        return temp;
    }

    public boolean Delete(Dictionary e)
    { 
        if(e==null)return false;
        BSTree curr=this.getRoot();   //setting current to the root sentinel node
        if(curr.right==null)return false;    //empty tree
        curr=curr.right;              //setting current to the right child of sentinel where actual tree starts ; it may be null;
        while(curr!=null && !(curr.key==e.key && curr.address==e.address && curr.size==e.size)){
            if(e.key<curr.key || (e.key==curr.key && e.address<=curr.address)){
                curr=curr.left;
            }
            else curr=curr.right;
        }
        if(curr==null)return false;
        else if(curr.left==null && curr.right==null){     // case 1- when both the childs are null
            if(curr.parent.left==curr){
                curr.parent.left=null;
                return true;
            }
            else{
                curr.parent.right=null;
                return true;
            }
        }
        else if(curr.left==null && curr.right!=null){
            if(curr.parent.left==curr){          // case 2- when the node has only right child
                curr.right.parent=curr.parent;
                curr.parent.left=curr.right;
                return true;
            }
            else{
                curr.right.parent=curr.parent;
                curr.parent.right=curr.right;
                return true;
            }
        }
        else if(curr.left!=null && curr.right==null){
            if(curr.parent.left==curr){          // case 3- when the node has only left child
                curr.left.parent=curr.parent;
                curr.parent.left=curr.left;
                return true;
            }
            else{
                curr.left.parent=curr.parent;
                curr.parent.right=curr.left;
                return true;
            }
        }
        else{                                   // case 4- when the node has both left and right child
            BSTree temp=curr.right;
            while(temp.left!=null)temp=temp.left;    // finding the successor node
            if(temp.right==null){                    // removing the successor node from its initial position
                if(temp.parent.left==temp)temp.parent.left=null;
                else temp.parent.right=null;
            }
            else{                                    // removing the successor node from its initial position
                if(temp.parent.left==temp){
                    temp.right.parent=temp.parent;
                    temp.parent.left=temp.right;
                }
                else{
                    temp.right.parent=temp.parent;
                    temp.parent.right=temp.right;
                }
            }
            // temp points to the successor node and curr points the node to be deleted
            if(curr.parent.left==curr)curr.parent.left=temp;
            else curr.parent.right=temp;
            temp.parent=curr.parent;
            temp.left=curr.left;
            temp.right=curr.right;
            if(temp.left!=null)temp.left.parent=temp;
            if(temp.right!=null)temp.right.parent=temp;
            return true;
        }
    }
        
    public BSTree Find(int key, boolean exact)
    { 
        /*BSTree temp=this.getFirst();
        while(temp!=null && temp.key<key)temp=temp.getNext();
        if(temp==null){return null;}
        else if(key==temp.key)return temp;
        else if(exact==false)return temp;
        else {return null;}*/
        BSTree temp=this.getRoot();
        BSTree soln=null;
        temp=temp.right;
        while(temp!=null){
            if(temp.key>=key)soln=temp;
            if(temp.key>=key){
                temp=temp.left;
            }
            else temp=temp.right;   
        }
        if(exact==true){
            if(soln!=null && soln.key==key)return soln;
            else return null;
        }
        return soln;
    }

    public BSTree getFirst()
    { 
        BSTree curr=this.getRoot();            //getting to the root sentinel node
        curr=curr.right;                 
        if(curr==null)return null;
        while(curr.left!=null)curr=curr.left;
        return curr;
    }

    public BSTree getNext()
    { 
        if(this.parent==null)return null;     // if called from the root sentinel
        BSTree curr=this;
        if(curr.right!=null){
            curr=curr.right;
            while(curr.left!=null)curr=curr.left;
            return curr;
        }

        //curr=curr.parent;
        while(curr!=null && curr.parent!=null && curr!=curr.parent.left)curr=curr.parent;
        if(curr==null || curr.parent==null)return null;
        return curr.parent;
    }


    private boolean ifBST(int lower_key, int lower_addr, int upper_key, int upper_addr){
        boolean t1=((this.key<upper_key) || (this.key==upper_key && this.address<=upper_addr))&& ((this.key>lower_key) || (this.key==lower_key && this.address>lower_addr));
        boolean tl=true;
        boolean tr=true;
        if(this.left!=null)tl=this.left.ifBST(lower_key,lower_addr,this.key,this.address);
        if(this.right!=null)tr=this.right.ifBST(this.key,this.address,upper_key,upper_addr);
        return (t1 && tl && tr);
    }

    private boolean relation(){
        if(this.left==null && this.right==null)return true;
        else if(this.left==null && this.right!=null){
            boolean t1=(this.right.parent==this);
            if(t1 && this.right.relation())return true;
            else return false;
        }
        else if(this.right==null && this.left!=null){
            boolean t1=(this.left.parent==this);
            if(t1 && this.left.relation())return true;
            else return false;
        }
        else{
            boolean t1=(this.left.parent==this);
            boolean t2=(this.right.parent==this);
            if(t1 && t2 && this.left.relation() && this.right.relation())return true;
            else return false;
        }
    }

    public boolean sanity()
    { 
        //checking that the tree follows the bst property that
        int minimum=Integer.MIN_VALUE;
        int maximum=Integer.MAX_VALUE;
        boolean isBST;
        BSTree curr=this.getRoot();
        if(curr.right==null)isBST=true;
        else{
            isBST=curr.right.ifBST(minimum,minimum,maximum,maximum);
        }
        if(isBST==false)return false;

        //checking that the inorder traversal gives sorted sequence of keys

        BSTree F=this.getFirst();
        BSTree S=null;
        if(F!=null)S=F.getNext();
        while(S!=null){
            if(F.key<S.key || (F.key==S.key && F.address<=S.address)){
                F=S;
                S=F.getNext();
            }
            else return false;
        }

        //checking left of sentinel is null and parent of sentinel is null
        BSTree sentin_root=this.getRoot();
        if(sentin_root.left!=null || sentin_root.parent!=null)return false;


        //checking the parent child relationship

        curr=sentin_root;
        boolean par_chi;
        if(curr.right==null)par_chi=true;
        else{
            par_chi=curr.right.relation();
        }
        if(par_chi==false)return false;

        return true; 
    }   
}


 


