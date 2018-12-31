package cs445.a5;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cs445.StackAndQueuePackage.*;

public class TernaryTree<E> implements TernaryTreeInterface<E>, TernaryTreeBonus<E>{

	private TernaryNode<E> root;

	public TernaryTree(){
		root = null;
	}

	public TernaryTree(E rootData){
		root = new TernaryNode<>(rootData);
	}

	public TernaryTree(E rootData, TernaryTree<E> leftTree,
                        TernaryTree<E> middleTree,
                        TernaryTree<E> rightTree){
		privateSetTree(rootData, leftTree, middleTree, rightTree);
	}

	/** Sets the ternary tree to a new one-node ternary tree with the given data
     *  @param rootData  The data for the new tree's root node
     */
    public void setTree(E rootData){
    	root = new TernaryNode<>(rootData);
    }

    /** Sets this ternary tree to a new ternary tree
     *  @param rootData  The data for the new tree's root node
     *  @param leftTree  The left subtree of the new tree
     *  @param middleTree  The middle subtree of the new tree
     *  @param rightTree  The right subtree of the new tree
     */
    public void setTree(E rootData, TernaryTreeInterface<E> leftTree,
                        TernaryTreeInterface<E> middleTree,
                        TernaryTreeInterface<E> rightTree){
    	privateSetTree(rootData, (TernaryTree<E>)leftTree, (TernaryTree<E>)middleTree, (TernaryTree<E>)rightTree);
    }

    private void privateSetTree(E rootData, TernaryTree<E> leftTree,
    					TernaryTree<E> middleTree, TernaryTree<E> rightTree){

    	root = new TernaryNode<>(rootData);

    	if ((leftTree != null) && !leftTree.isEmpty()) {
            root.setLeftChild(leftTree.root);
        }

        if ((middleTree != null) && !middleTree.isEmpty()) {
            if (middleTree != leftTree) {
                root.setMiddleChild(middleTree.root);
            } else {
                root.setMiddleChild(middleTree.root.copy());
            }
        }

        if ((rightTree != null) && !rightTree.isEmpty()) {
            if (rightTree != leftTree && rightTree != middleTree) {
                root.setRightChild(rightTree.root);
            } else {
                root.setRightChild(rightTree.root.copy());
            }
        }

        if ((leftTree != null) && (leftTree != this)) {
            leftTree.clear();
        }

        if ((middleTree != null) && (middleTree != this)){
        	middleTree.clear();
        }

        if ((rightTree != null) && (rightTree != this)) {
            rightTree.clear();
        }
    }

    public E getRootData(){
    	if (isEmpty()) {
            throw new EmptyTreeException();
        } else {
            return root.getData();
        }
    }


    protected void setRootData(E rootData) {
        root.setData(rootData);
    }

    protected void setRootNode(TernaryNode<E> rootNode) {
        root = rootNode;
    }

    protected TernaryNode<E> getRootNode() {
        return root;
    }

    /** Gets the height of the tree (i.e., the maximum number of nodes passed
     *  through from root to leaf, inclusive)
     *  @return  the height of the tree */
    public int getHeight(){
    	int height = 0;
        if (!isEmpty()) {
            height = root.getHeight();
        }
        return height;
    }

    /** Counts the total number of nodes in the tree
     *  @return  the number of nodes in the tree */
    public int getNumberOfNodes() {
        int numberOfNodes = 0;
        if (!isEmpty()) {
            numberOfNodes = root.getNumberOfNodes();
        }
        return numberOfNodes;
    }

    /** Determines whether the tree is empty (i.e., has no nodes)
     *  @return  true if the tree is empty, false otherwise */
    public boolean isEmpty() {
        return root == null;
    }

    /** Removes all data and nodes from the tree */
    public void clear() {
        root = null;
    }

    //I try to do Extra Credit
    /**
     * Determines whether the tree contains the given entry. Equality is
     * determined by using the .equals() method.
     * @param elem The entry to be searched for
     * @return true if elem is in the tree, false if not
     */
    public boolean contains(E elem){
    	Iterator iter = this.getPreorderIterator();
    	while(iter.hasNext()){
    		if(iter.next().equals(elem)){
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * Determines if the tree is "balanced," where a balanced tree is one where,
     * for any node in the tree, the heights of its subtrees differ by no more
     * than one.
     * @return true if the tree is balanced, false if there is a node whose
     * children have heights that differ by more than 1.
     */
    public boolean isBalanced(){
    	if(!isEmpty()){
    		return root.isBalanced();
    	}
    	return false;
    }

    public Iterator<E> getPreorderIterator() {
        return new PreorderIterator();
    }

   	//No Implementation, throw correct exception
    public Iterator<E> getInorderIterator() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    public Iterator<E> getPostorderIterator() {
        return new PostorderIterator();
    }

    public Iterator<E> getLevelOrderIterator() {
        return new LevelOrderIterator();
    }


    private class PreorderIterator implements Iterator<E> {
        private StackInterface<TernaryNode<E>> nodeStack;

        public PreorderIterator() {
            nodeStack = new LinkedStack<>();
            if (root != null) {
                nodeStack.push(root);
            }
        }

        public boolean hasNext() {
            return !nodeStack.isEmpty();
        }

        public E next() {
            TernaryNode<E> nextNode;

            if (hasNext()) {
                nextNode = nodeStack.pop();
                TernaryNode<E>[] children = (TernaryNode<E>[])new TernaryNode<?>[3];
                children[0] = nextNode.getLeftChild();
                children[1] = nextNode.getMiddleChild();
                children[2] = nextNode.getRightChild();

                if (children[2] != null) {
                    nodeStack.push(children[2]);
                }

                if(children[1] != null){
                	nodeStack.push(children[1]);
                }

                if (children[0] != null) {
                    nodeStack.push(children[0]);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class PostorderIterator implements Iterator<E> {
        private StackInterface<TernaryNode<E>> nodeStack;
        private TernaryNode<E> currentNode;

        public PostorderIterator() {
            nodeStack = new LinkedStack<>();
            currentNode = root;
        }

        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        }
        public E next() {

            TernaryNode<E> leftChild, nextNode = null;

            // Find leftmost leaf
            while (currentNode != null) {
                nodeStack.push(currentNode);
                leftChild = currentNode.getLeftChild();

                if(leftChild==null){
                	if(currentNode.getMiddleChild() == null){
                		currentNode = currentNode.getRightChild();
                	}else{
                		currentNode = currentNode.getMiddleChild();
                	}

                }else{
                	currentNode = leftChild;
                }
            }
            // Find leftmost leaf
            //a,b, ,d,e,f,g, , , ,k,l,m
            //f,e,b,a
           	

            // Stack is not empty either because we just pushed a node, or
            // it wasn'E empty to begin with since hasNext() is true.
            // But Iterator specifies an exception for next() in case
            // hasNext() is false.

            if (!nodeStack.isEmpty()) {
                nextNode = nodeStack.pop();
                // nextNode != null since stack was not empty before pop

                TernaryNode<E> parent = null;
                if (!nodeStack.isEmpty()) {
                    parent = nodeStack.peek();
                    if (nextNode == parent.getLeftChild()) {
                    	if(parent.getMiddleChild()==null){
                    		currentNode=parent.getRightChild();
                    	}else{
                    		currentNode = parent.getMiddleChild();
                    	}                   
                    } else if(nextNode == parent.getMiddleChild()){
                    	currentNode = parent.getRightChild();
                    } else {
                        currentNode = null;
                    }
                } else {
                    currentNode = null;
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }


        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class LevelOrderIterator implements Iterator<E> {
        private QueueInterface<TernaryNode<E>> nodeQueue;

        public LevelOrderIterator() {
            nodeQueue = new LinkedQueue<>();
            if (root != null) {
                nodeQueue.enqueue(root);
            }
        }

        public boolean hasNext() {
            return !nodeQueue.isEmpty();
        }

        public E next() {
            TernaryNode<E> nextNode;

            if (hasNext()) {
                nextNode = nodeQueue.dequeue();
                TernaryNode<E>[] children = (TernaryNode<E>[])new TernaryNode<?>[3];
                children[0] = nextNode.getLeftChild();
                children[1] = nextNode.getMiddleChild();
                children[2] = nextNode.getRightChild();

                // Add to queue in order of recursive calls
                if (children[0] != null) {
                    nodeQueue.enqueue(children[0]);
                }

                if(children[1] != null){
                	nodeQueue.enqueue(children[1]);
                }

                if (children[2] != null) {
                    nodeQueue.enqueue(children[2]);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}