export class Post {
  id: number;
  reviewId: number;
  title: string;
  content: string;
  author: string;
  status: string;
  category: Category;
  createdOn: Date;

  constructor(id: number, reviewId: number, title: string, content: string, author: string, status: string, category: Category, createdOn: Date) {
    this.id = id;
    this.reviewId = reviewId;
    this.title = title;
    this.content = content;
    this.author = author;
    this.status = status;
    this.category = category;
    this.createdOn = createdOn;
  }
}

export enum Category {
  TECHNOLOGY = 'TECHNOLOGY',
  HEALTH = 'HEALTH',
  EDUCATION = 'EDUCATION',
  ENTERTAINMENT = 'ENTERTAINMENT',
  SPORTS = 'SPORTS'
}
