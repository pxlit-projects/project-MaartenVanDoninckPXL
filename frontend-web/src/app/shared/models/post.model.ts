export class Post {
  reviewId: number;
  title: string;
  author: string;
  status: number;

  constructor(reviewId: number, title: string, author: string, status: number) {
    this.reviewId = reviewId;
    this.title = title;
    this.author = author;
    this.status = status;
  }
}
