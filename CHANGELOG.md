# [1.4.0](https://github.com/Bookwel-Team/bookwel-api/compare/v1.3.2...v1.4.0) (2024-02-08)


### Features

* whoami ([444186d](https://github.com/Bookwel-Team/bookwel-api/commit/444186d9d825ecb6601398bc19a8c91c8afe3f45))



## [1.3.2](https://github.com/Bookwel-Team/bookwel-api/compare/v1.3.1...v1.3.2) (2024-02-08)


### Bug Fixes

* change env variable referencement in typescript client ([99a7048](https://github.com/Bookwel-Team/bookwel-api/commit/99a70489bf67855723afddee77b86ae4f102c760))



## [1.3.1](https://github.com/Bookwel-Team/bookwel-api/compare/v1.3.0...v1.3.1) (2024-02-08)


### Bug Fixes

* **infra:** make api url as env variable in typescript client ([7cc972b](https://github.com/Bookwel-Team/bookwel-api/commit/7cc972ba3a0ffe55f4aff143fabae9417298d662))



# [1.3.0](https://github.com/Bookwel-Team/bookwel-api/compare/v1.2.1...v1.3.0) (2024-02-08)


### Bug Fixes

* missing aws.region for BucketConf ([3300138](https://github.com/Bookwel-Team/bookwel-api/commit/3300138c8a0a23216472c5b5265472a506985ce8))
* update imports after java client update ([370720c](https://github.com/Bookwel-Team/bookwel-api/commit/370720c8decb35b8093b86322a208b6807050457))
* user reactions are mishandled, you can only react one on a category / book and this is the reaction which gets updated all the time ([b464e87](https://github.com/Bookwel-Team/bookwel-api/commit/b464e87eb8a897167dc2da35b0edb0caa93f6a64))


### Features

* rest exceptions mapping, user creation endpoint is made by post method ([e7894f0](https://github.com/Bookwel-Team/bookwel-api/commit/e7894f047588a7ba007858df3ae1dad4d09600a8))
* s3 presigned url ([2290d49](https://github.com/Bookwel-Team/bookwel-api/commit/2290d49e4a2e0f0aa0cdc40a2257cbb1baed1aa4))
* **tests:** increase test coverage ([ddd1d85](https://github.com/Bookwel-Team/bookwel-api/commit/ddd1d855eea2115f0075e00b0beabd61df14d837))



## [1.2.1](https://github.com/Bookwel-Team/bookwel-api/compare/v1.2.0...v1.2.1) (2024-02-07)


### Bug Fixes

* **infra:** include necessary DOM librairies in typescript client ([71f8e26](https://github.com/Bookwel-Team/bookwel-api/commit/71f8e26e8e2a343d521761fe2d310fb28a6e3ba4))



# [1.2.0](https://github.com/Bookwel-Team/bookwel-api/compare/v1.1.0...v1.2.0) (2024-02-07)


### Bug Fixes

* enum db writing is done via @ColumnTransformer ([ef31fd0](https://github.com/Bookwel-Team/bookwel-api/commit/ef31fd089eb7c00d8d786102662aec2fd4ceb585))
* reacting on an entity checks whether a reaction already exists or not and updates it if so ([4e92436](https://github.com/Bookwel-Team/bookwel-api/commit/4e9243631b5ce9caf11f461979a427e759522804))
* script is not executable when building application ([bb0336d](https://github.com/Bookwel-Team/bookwel-api/commit/bb0336d0cec8056cf90d5fb441c80ea30497315c))


### Features

* authenticate using firebase idToken with firebaseAdmin ([61cbead](https://github.com/Bookwel-Team/bookwel-api/commit/61cbeadb4b70679ce0fa9403dda71d84c8277e13))
* books are linked to categories ([98db05d](https://github.com/Bookwel-Team/bookwel-api/commit/98db05d6729868ede7e54f461cfbbd0e1b9dc021))
* create user and update user profile ([5778e45](https://github.com/Bookwel-Team/bookwel-api/commit/5778e452638f504736ce2060ea543518cb969ce7))
* get book resources with the corresponding filters ([ef90f81](https://github.com/Bookwel-Team/bookwel-api/commit/ef90f81a148b702fae78a7429bd28556ba733c4d))
* implements user reactions endpoint ([845eb1b](https://github.com/Bookwel-Team/bookwel-api/commit/845eb1b4bc20720fd273f731eebf3204daee8072))
* update user_reactions doc for reactions on category and on book and default these to 'UNSET' ([7556fc8](https://github.com/Bookwel-Team/bookwel-api/commit/7556fc870317bd9616a7a18fd884139cd43cf6a0))



# [1.1.0](https://github.com/Bookwel-Team/bookwel-api/compare/1687a1a57f2ad8e626298f847fa2ef5c8e7076bc...v1.1.0) (2024-01-23)


### Features

* configure test containers ([3ca94bc](https://github.com/Bookwel-Team/bookwel-api/commit/3ca94bcbc796861ebdc466ca518cc4722bfba418))
* initialize project ([1687a1a](https://github.com/Bookwel-Team/bookwel-api/commit/1687a1a57f2ad8e626298f847fa2ef5c8e7076bc))



