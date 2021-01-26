// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.


    private AVLTree getRoot(){
        AVLTree curr=this;
        while(curr.parent!=null){
            curr=curr.parent;
        }
        return curr;
    }

    private int getHeight(AVLTree node){
        if(node==null)return 0;
        else return node.height;
    }

    

    private AVLTree rebalance(AVLTree nod){                   // helper function for rotation
        if(nod==null)return null;
        if(!((getHeight(nod.left)>getHeight(nod.right)+1)||(getHeight(nod.right)>getHeight(nod.left)+1)))return nod;
        AVLTree node=nod;
        AVLTree y=null,z=null;
        if(getHeight(node.left)>getHeight(node.right)){         
            y=node.left;
            if(y==null)return null;
            if(getHeight(node.left.left)>=getHeight(node.left.right)){      //case 1-- left left
                z=node.left.left;
                if(node.parent.left==node)node.parent.left=y;
                else node.parent.right=y;
                y.parent=node.parent;

                node.left=y.right;
                if(node.left!=null)node.left.parent=node;

                y.right=node;
                node.parent=y;

                node.height=getHeight(node.left)>getHeight(node.right)?getHeight(node.left)+1:getHeight(node.right)+1;
                z.height=getHeight(z.left)>getHeight(z.right)?getHeight(z.left)+1:getHeight(z.right)+1;
                y.height=getHeight(y.left)>getHeight(y.right)?getHeight(y.left)+1:getHeight(y.right)+1;
                
                return y;
            }
            else{                                                           //case 2-- left right                               
                z=node.left.right;
                if(node.parent.left==node)node.parent.left=z;
                else node.parent.right=z;
                if(z!=null)z.parent=node.parent;

                y.right=z.left;
                if(y.right!=null)y.right.parent=y;

                node.left=z.right;
                if(node.left!=null)node.left.parent=node;

                z.left=y;
                y.parent=z;

                z.right=node;
                node.parent=z;

                node.height=getHeight(node.left)>getHeight(node.right)?getHeight(node.left)+1:getHeight(node.right)+1;
                y.height=getHeight(y.left)>getHeight(y.right)?getHeight(y.left)+1:getHeight(y.right)+1;
                z.height=getHeight(z.left)>getHeight(z.right)?getHeight(z.left)+1:getHeight(z.right)+1;

                return z;

            }
        }

        else{
            y=node.right;
            if(y==null)return null;
            if(getHeight(node.right.right)>=getHeight(node.right.left)){     // case 3-- right right
                z=node.right.right;
                if(node.parent.right==node)node.parent.right=y;
                else node.parent.left=y;
                y.parent=node.parent;

                node.right=y.left;
                if(node.right!=null)node.right.parent=node;

                y.left=node;
                node.parent=y;

                node.height=getHeight(node.left)>getHeight(node.right)?getHeight(node.left)+1:getHeight(node.right)+1;
                z.height=getHeight(z.left)>getHeight(z.right)?getHeight(z.left)+1:getHeight(z.right)+1;
                y.height=getHeight(y.left)>getHeight(y.right)?getHeight(y.left)+1:getHeight(y.right)+1;
                
                return y;
            }
            else{                                                      // case 4-- right left    
                z=node.right.left;
                if(node.parent.right==node)node.parent.right=z;
                else node.parent.left=z;
                if(z!=null)z.parent=node.parent;

                y.left=z.right;
                if(y.left!=null)y.left.parent=y;

                node.right=z.left;
                if(node.right!=null)node.right.parent=node;

                z.right=y;
                y.parent=z;

                z.left=node;
                node.parent=z;

                node.height=getHeight(node.left)>getHeight(node.right)?getHeight(node.left)+1:getHeight(node.right)+1;
                y.height=getHeight(y.left)>getHeight(y.right)?getHeight(y.left)+1:getHeight(y.right)+1;
                z.height=getHeight(z.left)>getHeight(z.right)?getHeight(z.left)+1:getHeight(z.right)+1;

                return z;

            }
        }
    }

    private int max(int a,int b){
        return (a>b)?a:b;
    }

    private void bbalance(AVLTree nod){             // a helper function for height balancing   
        AVLTree node=nod;
        if(node==null || node.parent==null)return;
        while(node.parent!=null){
            int hL=getHeight(node.left);
            int hR=getHeight(node.right);
            node.height=max(hL,hR)+1;
            if(hL>hR+1 || hR>hL+1){
                node=rebalance(node);
                node=node.parent;
            }
            else{
                node.height=max(hL,hR)+1;
                node=node.parent;
            }
        }
    }


    public AVLTree Insert(int address, int size, int key){
        AVLTree newnode=new AVLTree(address,size,key);
        newnode.height=1;
        AVLTree curr=this.getRoot();
        if(curr.right==null){
            curr.right=newnode;
            newnode.parent=curr;
            newnode.height=1;
            return newnode;
        }

        curr=curr.right;
        AVLTree par=curr.parent;
        while(curr!=null){
            if(key<curr.key || (key==curr.key && address<=curr.address)){    // go in the left subtree
                par=curr;
                curr=curr.left;
            }
            else{                                                            // go in the right subtree
                par=curr;
                curr=curr.right;
            }
        }

        if(key<par.key || (key==par.key && address<=par.address)){
            par.left=newnode;
            newnode.parent=par;
            newnode.height=1;
            bbalance(newnode);
        }
        else{
            par.right=newnode;
            newnode.parent=par;
            newnode.height=1;
            bbalance(newnode);
        }
        return newnode;
    }

    public boolean Delete(Dictionary e)
    {
        if(e==null)return false;
        AVLTree curr=this.getRoot();   //setting current to the root sentinel node
        curr=curr.right;               //setting current to the right child of sentinel where actual tree starts ; it may be null;
        while(curr!=null && !(curr.key==e.key && curr.address==e.address && curr.size==e.size)){
            if(e.key<curr.key || (e.key==curr.key && e.address<=curr.address)){
                curr=curr.left;
            }
            else curr=curr.right;
        }
        AVLTree daddy;
        if(curr==null)return false;
        else daddy=curr.parent;
        
        if(daddy==null)return false;
        else if(curr.left==null && curr.right==null){     // case 1- when both the childs are null
            if(curr.parent.left==curr){
                curr.parent.left=null;
                bbalance(curr.parent);
                return true;
            }
            else{
                curr.parent.right=null;
                bbalance(curr.parent);
                return true;
            }
        }
        else if(curr.left==null && curr.right!=null){     // case 2- when the node has only right child
            if(curr.parent.left==curr){          
                curr.right.parent=curr.parent;
                curr.parent.left=curr.right;
                bbalance(curr.right);
                return true;
            }
            else{
                curr.right.parent=curr.parent;
                curr.parent.right=curr.right;
                bbalance(curr.right);
                return true;
            }
        }
        else if(curr.left!=null && curr.right==null){     // case 3- when the node has only left child
            if(curr.parent.left==curr){          
                curr.left.parent=curr.parent;
                curr.parent.left=curr.left;
                bbalance(curr.left);
                return true;
            }
            else{
                curr.left.parent=curr.parent;
                curr.parent.right=curr.left;
                bbalance(curr.left);
                return true;
            }
        }
        else{                                   // case 4- when the node has both left and right child
            AVLTree temp=curr.right;
            while(temp.left!=null)temp=temp.left;    // finding the successor node
            if(temp.right==null){                    // removing the successor node from its initial position
                if(temp.parent!=curr)daddy=temp.parent;
                else daddy=temp;
                if(temp.parent.left==temp)temp.parent.left=null;
                else temp.parent.right=null;
            }
            else{                                    // removing the successor node from its initial position
                if(temp.parent!=curr)daddy=temp.right;
                else daddy=temp.right;
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
            curr=null;
            if(temp.left!=null)temp.left.parent=temp;
            if(temp.right!=null)temp.right.parent=temp;
            bbalance(daddy);
            return true;
        }
        
    }


    public AVLTree Find(int k, boolean exact)
    { 
        AVLTree temp=this.getRoot();
        AVLTree soln=null;
        temp=temp.right;
        while(temp!=null){
            if(temp.key>=k)soln=temp;
            if(temp.key>=k){
                temp=temp.left;
            }
            else temp=temp.right;   
        }
        if(exact==true){
            if(soln!=null && soln.key==k)return soln;
            else return null;
        }
        return soln;
    }

    public AVLTree getFirst()
    { 
        AVLTree curr=this.getRoot();            //getting to the root sentinel node
        curr=curr.right;                 
        if(curr==null)return null;
        while(curr.left!=null)curr=curr.left;
        return curr;
    }

    public AVLTree getNext()
    {
        if(this.parent==null)return null;     // if called from the root sentinel
        AVLTree curr=this;
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

    private boolean ifBST(int lower_key, int lower_addr, int upper_key, int upper_addr){      //this function is same as in BSTree
        boolean t1=((this.key<upper_key) || (this.key==upper_key && this.address<=upper_addr))&& ((this.key>lower_key) || (this.key==lower_key && this.address>lower_addr));
        boolean tl=true;
        boolean tr=true;
        if(this.left!=null)tl=this.left.ifBST(lower_key,lower_addr,this.key,this.address);
        if(this.right!=null)tr=this.right.ifBST(this.key,this.address,upper_key,upper_addr);
        return (t1 && tl && tr);
    }

    private boolean relation(){            //this function is same as in BSTree 
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

    

    private boolean ifBalanced(){
        boolean t=!(getHeight(this.left)>getHeight(this.right)+1||getHeight(this.left)+1<getHeight(this.right));
        boolean t1=true;
        boolean t2=true;
        if(this.left!=null)t1=this.left.ifBalanced();
        if(this.right!=null)t2=this.right.ifBalanced();
        return(t && t1 && t2);
    }
    

    private boolean height_true(){
        boolean t=(this.height==max(getHeight(this.left),getHeight(this.right))+1);
        boolean t1=true;
        boolean t2=true;
        if(this.left!=null)t1=this.left.height_true();
        if(this.right!=null)t2=this.right.height_true();
        return(t && t1 && t2);
    }

    public boolean sanity()
    { 
        AVLTree temp=this.getRoot();

        if(temp.left!=null || temp.parent!=null)return false;


        if(temp.right==null)return true;
        temp=temp.right;
        int minimum=Integer.MIN_VALUE;
        int maximum=Integer.MAX_VALUE;

        // verifying that the avl tree also satisfies the BST properties
        boolean isBST=temp.ifBST(minimum,minimum,maximum,maximum);
        if(isBST==false)return false;

        // verifying the parent child relationship
        boolean par_chi=temp.relation();
        if(par_chi==false)return false;

        //checking that the height of each node is true
        boolean height_correct=temp.height_true();
        if(height_correct==false)return false;

        //checking that the tree is height balanced
        boolean isBalanced=temp.ifBalanced();
        if(isBalanced==false)return false;

        return true;


    }

    
}


