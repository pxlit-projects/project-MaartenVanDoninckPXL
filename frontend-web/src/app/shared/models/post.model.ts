export class Post {
  id: number;
  reviewId: number;
  title: string;
  content: string;
  author: string;
  status: string;

  constructor(id: number, reviewId: number, title: string, content: string, author: string, status: string) {
    this.id = id;
    this.reviewId = reviewId;
    this.title = title;
    this.content = content;
    this.author = author;
    this.status = status;
  }
}
