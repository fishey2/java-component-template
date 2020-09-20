[Spike] It's easy to slow down using hibernate and jpa.

Things to watch for:
* Multiple reads (page size)
* Writing pagination size aligned with reads?
* envers - auditing
* BigSerial not liked
* lookups?
* What is the process exactly (transactional)?
* Read -> Read -> Write
* What about spring batch (batching records)??



