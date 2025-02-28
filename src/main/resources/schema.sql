CREATE TABLE IF NOT EXISTS "blog_post" (
	"id" serial NOT NULL UNIQUE,
	"post_name" varchar(100) NOT NULL,
	"post_text" text NOT NULL,
	"post_image" bytea NOT NULL,
	"number_of_likes" bigint NOT NULL DEFAULT '0',
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "post_comment" (
	"id" serial NOT NULL UNIQUE,
	"post_id" bigint NOT NULL,
	"nickname" varchar(50) NOT NULL,
	"content" text NOT NULL,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "post_tag" (
	"id" serial NOT NULL UNIQUE,
	"tag_name" varchar(50) NOT NULL UNIQUE,
	PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "post_tag_relation" (
	"id" serial NOT NULL UNIQUE,
	"post_id" bigint NOT NULL,
	"tag_id" bigint NOT NULL,
	PRIMARY KEY ("id")
);


ALTER TABLE "post_comment" ADD CONSTRAINT "post_comment_fk1" FOREIGN KEY ("post_id") REFERENCES "blog_post"("id") ON DELETE CASCADE;
ALTER TABLE "post_tag_relation" ADD CONSTRAINT "post_tag_relation_fk1" FOREIGN KEY ("post_id") REFERENCES "blog_post"("id") ON DELETE CASCADE;
ALTER TABLE "post_tag_relation" ADD CONSTRAINT "post_tag_relation_fk2" FOREIGN KEY ("tag_id") REFERENCES "post_tag"("id");