//
// Created by Crainax on 2016/3/1.
//

#include <stdio.h>
#include <stdlib.h>

typedef char ElemType;

typedef enum {
    Link, Thread
} PointerTag;

typedef struct BiThrNode {
    ElemType data;
    struct BiThrNode *lchild, *rchild;
    PointerTag ltag, rtag;
} BiThrNode, *BiThrTree;

BiThrNode *pre = NULL;

void visit(ElemType data);

//前序递归创建一个未线索化的二叉树
//参数: tree 线索二叉树的根结点指针变量
void createBiThrTree(BiThrTree *tree) {
    char c;

    scanf("%c", &c);
    if (' ' == c) { //若输入的是空格,则不创建该结点,使得递归可以结束
        *tree = NULL;
    } else {

        *tree = ((BiThrTree) malloc(sizeof(BiThrNode)));
        //设置其数据域
        (*tree)->data = c;
        //未线索化的过程
        (*tree)->ltag = Link;
        (*tree)->rtag = Link;

        //递归生成其左子结点
        createBiThrTree(&(*tree)->lchild);
        //递归生成其右子结点
        createBiThrTree(&(*tree)->rchild);
    }
}

//中序遍历线索化
//参数: tree 线索二叉树的根结点指针变量
void inThreading(BiThrTree tree) {
    if (tree) {

        inThreading(tree->lchild); //递归左孩子线索化

        //中序遍历处理内容
        if (!tree->lchild) { //如果该结点没有左孩子,设置ltag为Thread,并把lChild指向前驱结点
            tree->ltag = Thread;
            tree->lchild = pre;
        }

        if (!pre->rchild) {//处理pre结点(也就是上一个创建的结点),若其无右孩子,则令pre的rChild指向后继结点
            pre->rtag = Thread;
            pre->rchild = tree;
        }

        pre = tree;//令pre全局变量指向已经处理完毕的结点,令下个结点继续处理
        //中序遍历处理结束

        inThreading(tree->rchild);//递归右孩子线索化
    }
}

//创建头结点.
//参数: head 作为指向头结点的结点指针变量, tree 线索二叉树的根结点指针变量
void inOrderThreading(BiThrTree *head, BiThrTree tree) {
    //创建头结点
    *head = (BiThrTree) malloc(sizeof(BiThrNode));
    //设置该头结点的左右标志位分别为非线索与线索
    //递归线索化的过程能使得第一个结点的前驱正确指向该头结点.
    (*head)->ltag = Link;
    (*head)->rtag = Thread;
    if (!tree) {
        //若根结点不存在,则该二叉树为空,让该头结点指向自身.
        (*head)->lchild = *head;
    } else {
        //令头结点的左指针指向根结点
        (*head)->lchild = tree;
        pre = *head;
        //开始递归输入线索化
        inThreading(tree);
        //此时结束了最后一个结点的线索化了,下面的代码把头结点的后继指向了最后一个结点.
        //并把最后一个结点的后继也指向头结点,此时树成为了一个类似链表的循环.
        pre->rchild = *head;
        pre->rtag = Thread;
        (*head)->rchild = pre;
    }
}

//以迭代的形式去中序遍历线索二叉树.
int inOrderTraverseThr(BiThrTree T) {

    BiThrTree p = T->lchild;    //令p先指向根节点.

    //退出循环的条件是p重新指回了头结点.
    while (p != T) {

        while (p->ltag == Link)
            p = p->lchild;  //迭代令p指向左子树为空的结点.

        visit(p->data);

        //如果该结点没有右子结点,则不执行下面while语句里面的语句,即令下次循环遍历该结点的右子结点.
        //如果下一个节点就是头结点,则也不进入下面while语句的循环体中.
        //下面的语句就是利用Thread的作用直接去判断,从而调用下一个需要遍历的结点(线索化的优点就出来了).
        while (p->rtag == Thread && p->rchild != T) {
            p = p->rchild;
            visit(p->data);
        }

        //令p指向下一个结点,可以是右子结点,也可以是后继结点,取决于此时tag域的信息.
        p = p->rchild;

    }


    return 1;
}

//访问该数据的函数,这里就简单的打印出来.
void visit(ElemType data) {
    printf("%c", data);
}

int main() {

    BiThrTree tree = NULL, head = NULL;

    //创建二叉树
    createBiThrTree(&tree);
    //线索化二叉树的过程
    inOrderThreading(&head, tree);
    printf("中序遍历的结点为:");
    //遍历整棵线索二叉树
    inOrderTraverseThr(head);

    printf("\n");
    return 1;
}
