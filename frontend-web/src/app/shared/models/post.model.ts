export class Post {
  id: number;
  reviewId: number;
  title: string;
  content: string;
  author: string;
  status: string;
  category: Category;

  constructor(id: number, reviewId: number, title: string, content: string, author: string, status: string, category: Category) {
    this.id = id;
    this.reviewId = reviewId;
    this.title = title;
    this.content = content;
    this.author = author;
    this.status = status;
    this.category = category;
  }
}

export enum Category {
  TECHNOLOGY = 'TECHNOLOGY',
  HEALTH = 'HEALTH',
  EDUCATION = 'EDUCATION',
  ENTERTAINMENT = 'ENTERTAINMENT',
  SPORTS = 'SPORTS'
}
