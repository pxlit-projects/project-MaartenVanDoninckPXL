export class Post {
  reviewId: number;
  title: string;
  content: string;
  author: string;
  status: string;

  constructor(reviewId: number, title: string, content: string, author: string, status: string) {
    this.reviewId = reviewId;
    this.title = title;
    this.content = content;
    this.author = author;
    this.status = status;
  }
}
