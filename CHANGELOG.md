## [1.8.2](https://github.com/Bookwel-Team/bookwel-api/compare/v1.8.1...v1.8.2) (2024-02-19)


### Bug Fixes

* upload book ([bdd4cd9](https://github.com/Bookwel-Team/bookwel-api/commit/bdd4cd925325feda307dfc0113b05875404d365d))



## [1.8.1](https://github.com/Bookwel-Team/bookwel-api/compare/v1.8.0...v1.8.1) (2024-02-18)


### Bug Fixes

* BookReaction Id gen is automated ([383739f](https://github.com/Bookwel-Team/bookwel-api/commit/383739fbdffd3e97c0b50c9bbac6d6c3fd62f21b))
* **docs:** make api url available in https ([88e20bd](https://github.com/Bookwel-Team/bookwel-api/commit/88e20bd12ddd2b1392e1955bbae816ba4977d66a))



# [1.8.0](https://github.com/Bookwel-Team/bookwel-api/compare/v1.6.1...v1.8.0) (2024-02-17)


### Bug Fixes

* bad request are not correctly mapped ([5633956](https://github.com/Bookwel-Team/bookwel-api/commit/56339569ea876ebdc39a9f0a0b0db7f27ada7c07))
* set pdf reading api url env var value ([8bbe4c5](https://github.com/Bookwel-Team/bookwel-api/commit/8bbe4c5f17e9c5b3eee12d8188c53668e83b9643))
* throw UsernameNotFound if user is not in db ([41acc3b](https://github.com/Bookwel-Team/bookwel-api/commit/41acc3b7c19c12400f823c961cf4d65112b0e73e))
* userReaction is not correctly mapped because we couldn't guess the currentUser's id on permitAll endpoints ([0dbb7df](https://github.com/Bookwel-Team/bookwel-api/commit/0dbb7dffb00d48b4537751168b4c55aabf4fd6ca))


### Features

* books now have pictures ([8f5c99a](https://github.com/Bookwel-Team/bookwel-api/commit/8f5c99a83385282369713a064d9ebb701cd4d779))
* create user with email, do not let email update on profile update ([aa3b724](https://github.com/Bookwel-Team/bookwel-api/commit/aa3b724a58591ca83fecd2d2ac1aab0873a92530))
* get author and title from external api, then map with posted book ([58dbf22](https://github.com/Bookwel-Team/bookwel-api/commit/58dbf22fc5704794016aa030e78425ee1fe983c0))



## [1.6.1](https://github.com/Bookwel-Team/bookwel-api/compare/v1.6.0...v1.6.1) (2024-02-12)


### Bug Fixes

* fixed case for all entities in docs ([870d2ec](https://github.com/Bookwel-Team/bookwel-api/commit/870d2ec3bb493cf2147d499040a5912c6058dec3))



# [1.6.0](https://github.com/Bookwel-Team/bookwel-api/compare/v1.5.0...v1.6.0) (2024-02-12)


### Features

* create book ([2418975](https://github.com/Bookwel-Team/bookwel-api/commit/2418975d817275e0f40cd1bb75822220470becd7))
* map books with their reactions ([3789733](https://github.com/Bookwel-Team/bookwel-api/commit/378973304a4ce0030bb895efef85db8518b263b8))
* map CategoryReactions to category ([c4810c0](https://github.com/Bookwel-Team/bookwel-api/commit/c4810c02ea0a3772267e5bfd2a9c767e8ed57fb6))
* remove get Reactions by book or category, refactor categoryReaction endpoint ([7dcb195](https://github.com/Bookwel-Team/bookwel-api/commit/7dcb1950270be4ef227f47d3ececda4e3314454a))



# [1.5.0](https://github.com/Bookwel-Team/bookwel-api/compare/v1.4.0...v1.5.0) (2024-02-09)


### Features

* ai book recommendation from liked / disliked books ([d051a08](https://github.com/Bookwel-Team/bookwel-api/commit/d051a0845a1297ad272b7b1798396db7f34e1c4e))
* filter books by title also ([401accd](https://github.com/Bookwel-Team/bookwel-api/commit/401accd7616714a0f2dad0fa390f060acea2420c))
* integrate ai chatbot ([13ae7a3](https://github.com/Bookwel-Team/bookwel-api/commit/13ae7a30faed919fc602b4cee82f0638ccd8e3a3))



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



