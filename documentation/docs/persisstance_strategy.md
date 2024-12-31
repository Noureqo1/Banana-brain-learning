## Persistence Strategy Overview

We utilize Hibernate (JPA) as our Object-Relational Mapping (ORM) framework.

### Entity Configuration
- All entity classes are marked with the `@Entity` annotation to identify them as JPA entities.
- The `@Id` annotation designates the primary key of each entity.
- Inheritance strategies are defined using the `@Inheritance` annotation.
- design patterns like dependency injunction are defined using `@autowired` annotation.

### Entity Relationships
- Relationships between entities, such as one-to-one associations, are configured using annotations like `@OneToOne`.

### Repository Layer
- Each entity is paired with a repository interface that extends `JpaRepository`, facilitating standard CRUD operations.

### Service Layer
- A dedicated service layer is implemented for each entity to encapsulate business logic and manage repository interactions.
- These services include methods for core operations, such as saving, retrieving, updating, and deleting entities.
