export class Review {
    postId: number;
    author: string;
    approval: boolean;
    content: string;

    constructor(postId: number, author: string, approval: boolean, content: string) {
        this.postId = postId;
        this.author = author;
        this.approval = approval;
        this.content = content;
    }
}