export class CommentRequest {
    postId: number;
    content: string;
    author: string;

    constructor(postId: number, content: string, author: string) {
        this.postId = postId;
        this.content = content;
        this.author = author;
    }
}