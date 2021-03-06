## aPublish

It's an anonymous immutable feed aggregator.

### JSON Schemas

#### Post Schema:
```json
{
  "id": 0,
  "author": "someone",
  "title": "something",
  "content": "text",
  "createdDate": 766800000,
  "lang": "en",
  "tags": [
    "life",
    "death"
  ]
}
```
* `id` is long value.
* `createdDate` is Unix timestamp in milliseconds.
* `lang` is 2-letter language code defined in [ISO 639](https://en.wikipedia.org/wiki/ISO_639).
* Fields `author`, `theme` and `lang` can be `null`.


#### Page schema:
```json
{
  "page": 0,
  "posts": [
    {
      "id": 0,
      "author": "someone",
      "title": "something",
      "content": "text",
      "createdDate": 766800000,
      "lang": "en",
      "tags": [
        "life",
        "death"
      ]
    }
  ],
  "hasNextPage": false
}
```
As you can see `posts` is just array of [Post schema](#post-schema), but `content` could be trim.
Also `posts` can be empty.

### Endpoints

#### Get page of 10 posts
> GET /api/{page}

Parameters:
* `page` (optional, integer, default 0) - page value

Response: [Page schema](#page-schema).

---
#### Get post by ID
> GET /api/post/{id}

Parameters:
* `id` (required, long) - ID of a post

Response: [Post schema](#post-schema) or code 404.

---
#### Create new post
> POST /api/post

Parameters:
* `title` (required, string, max length 64 chars) - title of this post
* `content` (required, string) - content of this post
* `author` (optional, string, max length 64 chars) - author of this post
* `tags` (optional, array of strings) - tags for this post
* `lang` (optional, string) - 2-letter language code

As you can see it's the same [Post schema](#post-schema), but without fields `id` and `createdDate`.

Example request:
```json
{
  "title": "How we do some cool stuff",
  "author": "Some cool guy",
  "content": "We just do it!",
  "lang": "en",
  "tags": [
    "life",
    "death"
  ]
}
```

Response:
code 201 and header "Location". 

---
#### Search posts by author
> GET /api/author/{author}/{page}

Parameters:
* `author` (required, string) - author that you want to search
* `page` (optional, integer, default 0) - page value

Response: [Page schema](#page-schema).

---
#### Search posts by tag
> GET /api/tag/{tag}/{page}

Parameters:
* `tag` (required, string) - tag that you want to search
* `page` (optional, integer, default 0) - page value

Response: [Page schema](#page-schema).